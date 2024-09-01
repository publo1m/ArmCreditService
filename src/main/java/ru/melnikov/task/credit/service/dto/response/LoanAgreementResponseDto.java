package ru.melnikov.task.credit.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanAgreementResponseDto {
    UUID agreementId;
    boolean isSigned;
    LocalDateTime dateSigned;
    BigDecimal loanAmount;
    int loanTermMonths;
}
