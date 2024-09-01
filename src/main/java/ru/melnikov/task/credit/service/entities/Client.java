package ru.melnikov.task.credit.service.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "client")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "client_id")
    private UUID clientId;

    @Column(name = "family_name")
    private String familyName;

    @Column(name = "name")
    private String name;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "passport_details")
    private String passportDetails;

    @Column(name = "marital_status")
    private String maritalStatus;

    @Column(name = "registration")
    private String registration;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "employment_details")
    private String employmentDetails;

    @OneToMany(mappedBy = "client",cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LoanAgreement> loanAgreementList;

    @OneToMany(mappedBy = "client", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LoanApplication> loanApplicationList;
}
