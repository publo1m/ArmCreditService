package ru.melnikov.task.credit.service.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.melnikov.task.credit.service.dao.SystemManagementRepository;
import ru.melnikov.task.credit.service.dto.request.ClientPersonalDataRequestDto;
import ru.melnikov.task.credit.service.dto.response.ClientResponseDto;
import ru.melnikov.task.credit.service.dto.response.LoanAgreementResponseDto;
import ru.melnikov.task.credit.service.dto.response.LoanApplicationResponseDto;
import ru.melnikov.task.credit.service.services.SystemManagementService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SystemManagementServiceImpl implements SystemManagementService {

    private final SystemManagementRepository managementRepository;

    @Override
    public List<ClientResponseDto> findClientByPersonalData(ClientPersonalDataRequestDto requestDto) {
        return managementRepository.findClientByClientPersonalDataRequestDto(requestDto);
    }

    @Override
    public List<ClientResponseDto> getAllClients(int page, int size) {
        return managementRepository.getAllClients(page, size);
    }

    @Override
    public List<LoanAgreementResponseDto> getAllAgreements(int page, int size) {
        return managementRepository.getAllAgreements(page, size);
    }

    @Override
    public List<LoanApplicationResponseDto> getAllApplications(int page, int size) {
        return managementRepository.getAllApplications(page, size);
    }

    @Override
    public long getTotalApplicationsCount() {
        return managementRepository.getTotalApplicationsCount();
    }

    @Override
    public long getTotalAgreementsCount() {
        return managementRepository.getTotalAgreementsCount();
    }

    @Override
    public long getTotalClientsCount() {
        return managementRepository.getTotalClientsCount();
    }
}
