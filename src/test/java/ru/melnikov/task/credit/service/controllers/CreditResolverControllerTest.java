package ru.melnikov.task.credit.service.controllers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.melnikov.task.credit.service.controllers.handler.GlobalExceptionHandler;
import ru.melnikov.task.credit.service.dto.request.LoanApplicationRequestDto;
import ru.melnikov.task.credit.service.entities.LoanAgreement;
import ru.melnikov.task.credit.service.services.CreditResolverService;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class CreditResolverControllerTest {

    @Mock
    private CreditResolverService creditResolverService;

    @InjectMocks
    private CreditResolverController creditResolverController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(creditResolverController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @SneakyThrows
    void getMainPage() {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("main/MainPage"))
                .andDo(print());
    }

    @Test
    @SneakyThrows
    void getApplicationForm() {
        mockMvc.perform(get("/getApplicationForm"))
                .andExpect(status().isOk())
                .andExpect(view().name("forms/ApplicationForm"))
                .andExpect(model().attributeExists("requestDto"))
                .andDo(print());
    }

    @Test
    @SneakyThrows
    void sendApplication_WithValidData_shouldReturnOk() {
        LoanAgreement mockAgreement = new LoanAgreement();
        when(creditResolverService.createApplication(any(LoanApplicationRequestDto.class)))
                .thenReturn(mockAgreement);

        mockMvc.perform(post("/sendApplication")
                        .param("familyName", "Family")
                        .param("name", "Name")
                        .param("patronymic", "Patronymic")
                        .param("passportDetails", "1212909090")
                        .param("maritalStatus", "Single")
                        .param("registration", "Registration, 9")
                        .param("contactNumber", "89999990000")
                        .param("employmentDetails", "worker")
                        .param("loanAmount", "10000")
                        .param("loanTermMonths", "4")
                        .param("amount", "1000"))
                .andExpect(status().isOk())
                .andExpect(view().name("forms/ContractSignaturePage"))
                .andExpect(model().attributeExists("loanAgreement"))
                .andDo(print());

        verify(creditResolverService, times(1))
                .createApplication(any(LoanApplicationRequestDto.class));
    }

    @Test
    @SneakyThrows
    void sendApplication_WithInvalidData_shouldThrowException() {
        mockMvc.perform(post("/sendApplication")
                        .param("firstName", "")
                        .param("lastName", "Doe"))
                .andExpect(status().isOk())
                .andExpect(view().name("exceptions/ValidationErrorPage"))
                .andExpect(model().attributeExists("validationErrors"))
                .andDo(print());

        verify(creditResolverService, times(0))
                .createApplication(any(LoanApplicationRequestDto.class));
    }

    @Test
    @SneakyThrows
    void signAgreement() {
        UUID agreementId = UUID.randomUUID();

        mockMvc.perform(post("/signAgreement")
                        .param("agreementId", agreementId.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("main/MainPage"))
                .andDo(print());

        verify(creditResolverService).signAgreement(agreementId);
    }

    @Test
    @SneakyThrows
    void declineAgreement() {
        UUID agreementId = UUID.randomUUID();

        mockMvc.perform(post("/declineAgreement")
                        .param("agreementId", agreementId.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("main/MainPage"))
                .andDo(print());

        verify(creditResolverService).declineAgreement(agreementId);
    }
}
