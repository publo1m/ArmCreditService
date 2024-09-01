package ru.melnikov.task.credit.service.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.melnikov.task.credit.service.dto.request.LoanApplicationRequestDto;
import ru.melnikov.task.credit.service.entities.LoanApplication;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class LoanApplicationMapperTest {

    private LoanApplicationMapper loanApplicationMapper;

    @BeforeEach
    void setUp() {
        loanApplicationMapper = Mappers.getMapper(LoanApplicationMapper.class);
    }

    @Test
    void testLoanApplicationDtoToEntity() {
        LoanApplicationRequestDto requestDto = new LoanApplicationRequestDto();
        requestDto.setLoanAmount("120000");
        requestDto.setLoanTermMonths("3");

        LoanApplication loanApplication = loanApplicationMapper.loanApplicationDtoToEntity(requestDto);

        int expectLoanTermMonths = 3;
        assertFalse(loanApplication.isApprovedLoan());
        assertEquals(new BigDecimal("120000"), loanApplication.getLoanAmount());
        assertEquals(expectLoanTermMonths, loanApplication.getLoanTermMonths());
    }
}
