package ru.melnikov.task.credit.service.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.melnikov.task.credit.service.dto.request.LoanApplicationRequestDto;
import ru.melnikov.task.credit.service.entities.LoanAgreement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

class LoanAgreementMapperTest {

    private LoanAgreementMapper loanAgreementMapper;

    @BeforeEach
    void setUp() {
        loanAgreementMapper = Mappers.getMapper(LoanAgreementMapper.class);
    }

    @Test
    void testRequestDtoToLoanAgreement() {
        LoanApplicationRequestDto requestDto = new LoanApplicationRequestDto();

        LoanAgreement loanAgreement = loanAgreementMapper.requestDtoToLoanAgreement(requestDto);

        assertFalse(loanAgreement.isSigned());
        assertNull(loanAgreement.getDateSigning());
        assertNull(loanAgreement.getLoanApplication());
    }
}
