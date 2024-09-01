package ru.melnikov.task.credit.service.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.melnikov.task.credit.service.dao.SystemManagementRepository;
import ru.melnikov.task.credit.service.dto.request.ClientPersonalDataRequestDto;
import ru.melnikov.task.credit.service.dto.response.ClientResponseDto;
import ru.melnikov.task.credit.service.dto.response.LoanAgreementResponseDto;
import ru.melnikov.task.credit.service.dto.response.LoanApplicationResponseDto;
import ru.melnikov.task.credit.service.services.impl.SystemManagementServiceImpl;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class SystemManagementServiceImplTest {

    @Mock
    private SystemManagementRepository managementRepository;

    @InjectMocks
    private SystemManagementServiceImpl systemManagementService;

    private ClientPersonalDataRequestDto requestDto;
    private List<ClientResponseDto> listClientDto;
    private List<LoanAgreementResponseDto> listLoanAgreementDto;
    private List<LoanApplicationResponseDto> listLoanApplicationDto;

    @BeforeEach
    void setUp() {
        requestDto = new ClientPersonalDataRequestDto();
        listClientDto = Collections.singletonList(new ClientResponseDto());
        listLoanAgreementDto = Collections.singletonList(new LoanAgreementResponseDto());
        listLoanApplicationDto = Collections.singletonList(new LoanApplicationResponseDto());
    }

    @Test
    void findClientByPersonalData_ShouldReturnClientResponseDtoList() {
        when(managementRepository.findClientByClientPersonalDataRequestDto(requestDto)).thenReturn(listClientDto);

        List<ClientResponseDto> result = systemManagementService.findClientByPersonalData(requestDto);

        assertNotNull(result);
        assertEquals(listClientDto, result);
        verify(managementRepository, times(1)).findClientByClientPersonalDataRequestDto(requestDto);
    }

    @Test
    void getAllClients_ShouldReturnClientResponseDtoList() {
        int page = 0;
        int size = 10;
        when(managementRepository.getAllClients(page, size)).thenReturn(listClientDto);

        List<ClientResponseDto> result = systemManagementService.getAllClients(page, size);

        assertNotNull(result);
        assertEquals(listClientDto, result);
        verify(managementRepository, times(1)).getAllClients(page, size);
    }

    @Test
    void getAllAgreements_ShouldReturnLoanAgreementResponseDtoList() {
        int page = 0;
        int size = 10;
        when(managementRepository.getAllAgreements(page, size)).thenReturn(listLoanAgreementDto);

        List<LoanAgreementResponseDto> result = systemManagementService.getAllAgreements(page, size);

        assertNotNull(result);
        assertEquals(listLoanAgreementDto, result);
        verify(managementRepository, times(1)).getAllAgreements(page, size);
    }

    @Test
    void getAllApplications_ShouldReturnLoanApplicationResponseDtoList() {
        int page = 0;
        int size = 10;
        when(managementRepository.getAllApplications(page, size)).thenReturn(listLoanApplicationDto);

        List<LoanApplicationResponseDto> result = systemManagementService.getAllApplications(page, size);

        assertNotNull(result);
        assertEquals(listLoanApplicationDto, result);
        verify(managementRepository, times(1)).getAllApplications(page, size);
    }

    @Test
    void getTotalApplicationsCount_ShouldReturnCount() {
        long expectedCount = 5L;
        when(managementRepository.getTotalApplicationsCount()).thenReturn(expectedCount);

        long result = systemManagementService.getTotalApplicationsCount();

        assertEquals(expectedCount, result);
        verify(managementRepository, times(1)).getTotalApplicationsCount();
    }

    @Test
    void getTotalAgreementsCount_ShouldReturnCount() {
        long expectedCount = 3L;
        when(managementRepository.getTotalAgreementsCount()).thenReturn(expectedCount);

        long result = systemManagementService.getTotalAgreementsCount();

        assertEquals(expectedCount, result);
        verify(managementRepository, times(1)).getTotalAgreementsCount();
    }

    @Test
    void getTotalClientsCount_ShouldReturnCount() {
        long expectedCount = 10L;
        when(managementRepository.getTotalClientsCount()).thenReturn(expectedCount);

        long result = systemManagementService.getTotalClientsCount();

        assertEquals(expectedCount, result);
        verify(managementRepository, times(1)).getTotalClientsCount();
    }
}
