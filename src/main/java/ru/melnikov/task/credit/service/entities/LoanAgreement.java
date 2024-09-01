package ru.melnikov.task.credit.service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "loan_agreement")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoanAgreement {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "loan_agreement_id")
    private UUID loanAgreementId;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToOne
    @JoinColumn(name = "loan_application_id")
    private LoanApplication loanApplication;

    @Column(name = "is_signed")
    private boolean isSigned;

    @Column(name = "date_of_signing")
    private LocalDateTime dateSigning;
}