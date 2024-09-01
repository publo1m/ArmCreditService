package ru.melnikov.task.credit.service.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.melnikov.task.credit.service.dao.CreditResolverRepository;
import ru.melnikov.task.credit.service.dto.request.LoanApplicationRequestDto;
import ru.melnikov.task.credit.service.entities.Client;
import ru.melnikov.task.credit.service.entities.LoanAgreement;
import ru.melnikov.task.credit.service.entities.LoanApplication;
import ru.melnikov.task.credit.service.exceptions.CreditRefusalException;
import ru.melnikov.task.credit.service.services.CreditResolverService;

import java.util.Random;
import java.util.UUID;

import static ru.melnikov.task.credit.service.mappers.ClientMapper.CLIENT_MAPPER;
import static ru.melnikov.task.credit.service.mappers.LoanAgreementMapper.LOAN_AGREEMENT_MAPPER;
import static ru.melnikov.task.credit.service.mappers.LoanApplicationMapper.LOAN_APPLICATION_MAPPER;

@Service
@RequiredArgsConstructor
public class CreditResolverServiceImpl implements CreditResolverService {

    private final CreditResolverRepository creditResolverRepository;

    @Override
    public LoanAgreement createApplication(LoanApplicationRequestDto requestDto) {
        LoanAgreement loanAgreement = LOAN_AGREEMENT_MAPPER.requestDtoToLoanAgreement(requestDto);
        LoanApplication application = LOAN_APPLICATION_MAPPER.loanApplicationDtoToEntity(requestDto);
        Client client = creditResolverRepository.findClientByLoanApplicationRequestDto(requestDto);

        if (client == null) {
            client = creditResolverRepository.insertClient(CLIENT_MAPPER.requestDtoToClient(requestDto));
        }

        LoanApplication existLoanApplication = creditResolverRepository.insertLoanApplication(application, client);
        boolean isApprovedLoan = isApprovedLoan();
        if (!isApprovedLoan) throw new CreditRefusalException();
        creditResolverRepository.updateStatusApplication(existLoanApplication.getLoanApplicationId());

        return creditResolverRepository.insertLoanAgreement(loanAgreement, client, existLoanApplication);
    }

    @Override
    public void signAgreement(UUID agreementId) {
        creditResolverRepository.signAgreement(agreementId);
    }

    @Override
    public void declineAgreement(UUID agreementId) {
        creditResolverRepository.declineAgreement(agreementId);
    }

    @Override
    public boolean isApprovedLoan() {
        return new Random().nextBoolean();
    }
}
