package ru.melnikov.task.credit.service.controllers.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ru.melnikov.task.credit.service.exceptions.CreditRefusalException;
import ru.melnikov.task.credit.service.exceptions.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGeneralException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
        modelAndView.addObject("errorResponse", errorResponse);
        modelAndView.setViewName("exceptions/ExceptionPage");
        return modelAndView;
    }

    @ExceptionHandler(CreditRefusalException.class)
    public ModelAndView handleCreditRefusalException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView("display/UnsuccessfulAttempt");
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                HttpStatus.FORBIDDEN);
        modelAndView.addObject("errorResponse", errorResponse);
        return modelAndView;
    }
}

