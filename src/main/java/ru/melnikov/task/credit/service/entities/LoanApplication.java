package ru.melnikov.task.credit.service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "loan_application")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "loan_application_id")
    private UUID loanApplicationId;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "is_approved_loan")
    private boolean isApprovedLoan;

    @Column(name = "loan_amount")
    private BigDecimal loanAmount;

    @Column(name = "loan_term_months")
    private Integer loanTermMonths;
}
