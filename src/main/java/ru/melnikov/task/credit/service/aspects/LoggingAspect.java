package ru.melnikov.task.credit.service.aspects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Aspect
@Slf4j(topic = "ArmCreditService")
@RequiredArgsConstructor
@Component
public class LoggingAspect {

    private final ObjectMapper objectMapper;

    @Pointcut("execution(public * ru.melnikov.task.credit.service.controllers.*.*(..))")
    public void controllersPointcut() {
    }

    @Pointcut("execution(* ru.melnikov.task.credit.service.services.impl.*.*(..))")
    public void servicesPointcut() {
    }

    @Pointcut("within(ru.melnikov.task.credit.service.controllers.handler.*)")
    public void controllerAdvicePointcut() {
    }

    @SneakyThrows
    @Before(value = "controllersPointcut()")
    public void loggingControllers(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String path = request.getRequestURI();
        String httpMethod = request.getMethod();
        log.info("Вызов эндпоинта по пути {} {}, контроллер: {}, метод: {}", path, httpMethod, className, methodName);
    }

    @Before(value = "servicesPointcut()")
    public void loggingServices(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String params = Arrays.stream(joinPoint.getArgs())
                .map(obj -> {
                    try {
                        return objectMapper.writeValueAsString(obj);
                    } catch (JsonProcessingException e) {
                        log.warn("Ошибка сериализации параметров для записи в лог. Сервис: {}, метод: {}", methodName, className, e);
                    }
                    return null;
                }).collect(Collectors.joining(", "));
        if (params.isEmpty()) {
            params = "Без параметров";
        }
        log.info("Вызов метода {}, переданные параметры: {}, сервис: {}", methodName, params, className);
    }

    @After(value = "controllerAdvicePointcut()")
    public void loggingControllerAdvice(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        StringBuilder exception = new StringBuilder();
        StringBuilder message = new StringBuilder();
        Optional<Throwable> exceptionsOp = Arrays.stream(joinPoint.getArgs())
                .filter(arg -> arg instanceof Throwable)
                .map(Throwable.class::cast).findFirst();
        if (exceptionsOp.isPresent()) {
            exception.append(exceptionsOp.get().getClass().getSimpleName());
            message.append(exceptionsOp.get().getMessage());
        }
        log.info("Перехвачено исключение {}, сообщение: {}, контроллер: {}, метод: {}",
                exception, message, className, joinPoint.getSignature().getName());
    }

    @AfterThrowing(value = "servicesPointcut()", throwing = "exception")
    public void afterThrowingAdvice(JoinPoint joinPoint, Throwable exception) {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        log.warn("Исключение {} в методе {}, класс: {}. Сообщение: {}",
                exception.getClass().getSimpleName(), methodName, className, exception.getMessage());
    }
}
