package ru.melnikov.task.credit.service.services;

import ru.melnikov.task.credit.service.dto.request.LoanApplicationRequestDto;
import ru.melnikov.task.credit.service.entities.LoanAgreement;

import java.util.UUID;

public interface CreditResolverService {

    LoanAgreement createApplication(LoanApplicationRequestDto requestDto);
    void signAgreement(UUID agreementId);
    void declineAgreement(UUID agreementId);
    boolean isApprovedLoan();
}
