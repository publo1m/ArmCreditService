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
import ru.melnikov.task.credit.service.dto.request.ClientPersonalDataRequestDto;
import ru.melnikov.task.credit.service.dto.response.ClientResponseDto;
import ru.melnikov.task.credit.service.dto.response.LoanAgreementResponseDto;
import ru.melnikov.task.credit.service.dto.response.LoanApplicationResponseDto;
import ru.melnikov.task.credit.service.services.SystemManagementService;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
public class SystemManagementRepositoryTest {

    @Mock
    private SystemManagementService managementService;

    @InjectMocks
    private SystemManagementController managementController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(managementController)
                .build();
    }

    @Test
    @SneakyThrows
    void getAllClients_ShouldReturnAllClientsPage() {
        List<ClientResponseDto> clients = Collections.singletonList(new ClientResponseDto());
        when(managementService.getAllClients(0, 10)).thenReturn(clients);
        when(managementService.getTotalClientsCount()).thenReturn(1L);

        mockMvc.perform(get("/getAllClients")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("display/AllClients"))
                .andExpect(model().attributeExists("clientList"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("size"));

        verify(managementService, times(1)).getAllClients(0, 10);
        verify(managementService, times(1)).getTotalClientsCount();
    }

    @Test
    @SneakyThrows
    void getSearchForm_ShouldReturnSearchFormPage() {
        mockMvc.perform(get("/getSearchForm"))
                .andExpect(status().isOk())
                .andExpect(view().name("forms/SearchForm"))
                .andExpect(model().attributeExists("requestDto"));
    }

    @Test
    @SneakyThrows
    void findClient_ShouldReturnAllClientsPage_WhenClientsFound() {
        List<ClientResponseDto> clients = Collections.singletonList(new ClientResponseDto());
        ClientPersonalDataRequestDto requestDto = new ClientPersonalDataRequestDto();
        when(managementService.findClientByPersonalData(requestDto)).thenReturn(clients);

        mockMvc.perform(post("/findClient")
                        .param("page", "0")
                        .param("size", "10")
                        .flashAttr("requestDto", requestDto))
                .andExpect(status().isOk())
                .andExpect(view().name("display/AllClients"))
                .andExpect(model().attributeExists("clientList"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("size"));

        verify(managementService, times(1)).findClientByPersonalData(requestDto);
    }

    @Test
    @SneakyThrows
    void findClient_ShouldReturnUnsuccessfulSearchClientPage_WhenClientsNotFound() {
        ClientPersonalDataRequestDto requestDto = new ClientPersonalDataRequestDto();
        when(managementService.findClientByPersonalData(requestDto)).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/findClient")
                        .param("page", "0")
                        .param("size", "10")
                        .flashAttr("requestDto", requestDto))
                .andExpect(status().isOk())
                .andExpect(view().name("display/UnsuccessfulSearchClient"));

        verify(managementService, times(1)).findClientByPersonalData(requestDto);
    }

    @Test
    @SneakyThrows
    void getAllAgreements_ShouldReturnAllAgreementsPage() {
        List<LoanAgreementResponseDto> agreements = Collections.singletonList(new LoanAgreementResponseDto());
        when(managementService.getAllAgreements(0, 10)).thenReturn(agreements);
        when(managementService.getTotalAgreementsCount()).thenReturn(1L);

        mockMvc.perform(get("/getAllAgreements")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("display/AllAgreements"))
                .andExpect(model().attributeExists("agreementList"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("size"));

        verify(managementService, times(1)).getAllAgreements(0, 10);
        verify(managementService, times(1)).getTotalAgreementsCount();
    }

    @Test
    @SneakyThrows
    void getAllApplications_ShouldReturnAllApplicationsPage() {
        List<LoanApplicationResponseDto> applications = Collections.singletonList(new LoanApplicationResponseDto());
        when(managementService.getAllApplications(0, 10)).thenReturn(applications);
        when(managementService.getTotalApplicationsCount()).thenReturn(1L);

        mockMvc.perform(get("/getAllApplications")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("display/AllApplications"))
                .andExpect(model().attributeExists("applicationList"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("size"));

        verify(managementService, times(1)).getAllApplications(0, 10);
        verify(managementService, times(1)).getTotalApplicationsCount();
    }
}
