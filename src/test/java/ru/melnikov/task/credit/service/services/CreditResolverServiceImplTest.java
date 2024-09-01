package ru.melnikov.task.credit.service.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.melnikov.task.credit.service.dao.CreditResolverRepository;
import ru.melnikov.task.credit.service.dto.request.LoanApplicationRequestDto;
import ru.melnikov.task.credit.service.entities.Client;
import ru.melnikov.task.credit.service.entities.LoanAgreement;
import ru.melnikov.task.credit.service.entities.LoanApplication;
import ru.melnikov.task.credit.service.exceptions.CreditRefusalException;
import ru.melnikov.task.credit.service.services.impl.CreditResolverServiceImpl;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreditResolverServiceImplTest {

    @Mock
    private CreditResolverRepository creditResolverRepository;

    private CreditResolverServiceImpl creditResolverService;
    private LoanApplicationRequestDto requestDto;
    private Client client;
    private LoanApplication loanApplication;
    private LoanAgreement loanAgreement;

    @BeforeEach
    void setUp() {
        creditResolverService = Mockito.spy(new CreditResolverServiceImpl(creditResolverRepository));
        requestDto = new LoanApplicationRequestDto();
        client = new Client();
        loanApplication = new LoanApplication();
        loanApplication.setLoanApplicationId(UUID.randomUUID());
        loanAgreement = new LoanAgreement();
    }

    @Test
    void createApplication_ShouldReturnLoanAgreement_WhenLoanIsApproved() {
        when(creditResolverRepository.findClientByLoanApplicationRequestDto(requestDto)).thenReturn(null);
        when(creditResolverRepository.insertClient(any(Client.class))).thenReturn(client);
        when(creditResolverRepository.insertLoanApplication(any(LoanApplication.class), any(Client.class))).thenReturn(loanApplication);
        when(creditResolverRepository.insertLoanAgreement(any(LoanAgreement.class), any(Client.class), any(LoanApplication.class)))
                .thenReturn(loanAgreement);

        when(creditResolverService.isApprovedLoan()).thenReturn(true);

        LoanAgreement result = creditResolverService.createApplication(requestDto);

        assertNotNull(result);
        verify(creditResolverRepository, times(1)).insertClient(any(Client.class));
        verify(creditResolverRepository, times(1))
                .insertLoanApplication(any(LoanApplication.class), any(Client.class));
        verify(creditResolverRepository, times(1))
                .updateStatusApplication(any(UUID.class));
        verify(creditResolverRepository, times(1))
                .insertLoanAgreement(any(LoanAgreement.class), any(Client.class), any(LoanApplication.class));
    }

    @Test
    void createApplication_ShouldThrowCreditRefusalException_WhenLoanIsNotApproved() {
        when(creditResolverRepository.findClientByLoanApplicationRequestDto(requestDto)).thenReturn(client);
        when(creditResolverService.isApprovedLoan()).thenReturn(false);

        assertThrows(CreditRefusalException.class, () -> creditResolverService.createApplication(requestDto));

        verify(creditResolverRepository, times(1)).findClientByLoanApplicationRequestDto(requestDto);
        verify(creditResolverRepository, times(1)).insertLoanApplication(any(LoanApplication.class), any(Client.class));
        verify(creditResolverRepository, never()).updateStatusApplication(any(UUID.class));
        verify(creditResolverRepository, never()).insertLoanAgreement(any(LoanAgreement.class), any(Client.class), any(LoanApplication.class));
    }

    @Test
    void signAgreement_ShouldCallSignAgreementOnRepository() {
        UUID agreementId = UUID.randomUUID();
        creditResolverService.signAgreement(agreementId);
        verify(creditResolverRepository, times(1)).signAgreement(agreementId);
    }

    @Test
    void declineAgreement_ShouldCallDeclineAgreementOnRepository() {
        UUID agreementId = UUID.randomUUID();
        creditResolverService.declineAgreement(agreementId);
        verify(creditResolverRepository, times(1)).declineAgreement(agreementId);
    }
}
