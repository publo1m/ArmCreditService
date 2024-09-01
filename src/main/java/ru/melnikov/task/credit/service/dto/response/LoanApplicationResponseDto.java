package ru.melnikov.task.credit.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplicationResponseDto {
    UUID applicationId;
    boolean isApprovedStatus;
    private BigDecimal loanAmount;
    private Integer loanTermMonths;
}
