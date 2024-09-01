package ru.melnikov.task.credit.service.services;

import ru.melnikov.task.credit.service.dto.request.ClientPersonalDataRequestDto;
import ru.melnikov.task.credit.service.dto.response.ClientResponseDto;
import ru.melnikov.task.credit.service.dto.response.LoanAgreementResponseDto;
import ru.melnikov.task.credit.service.dto.response.LoanApplicationResponseDto;

import java.util.List;

public interface SystemManagementService {
    List<ClientResponseDto> findClientByPersonalData(ClientPersonalDataRequestDto requestDto);
    List<ClientResponseDto> getAllClients(int page, int size);
    List<LoanAgreementResponseDto> getAllAgreements(int page, int size);
    List<LoanApplicationResponseDto> getAllApplications(int page, int size);
    long getTotalClientsCount();
    long getTotalAgreementsCount();
    long getTotalApplicationsCount();
}
