package ru.melnikov.task.credit.service.data;

import ru.melnikov.task.credit.service.dto.request.ClientPersonalDataRequestDto;
import ru.melnikov.task.credit.service.dto.request.LoanApplicationRequestDto;
import ru.melnikov.task.credit.service.entities.Client;
import ru.melnikov.task.credit.service.entities.LoanAgreement;
import ru.melnikov.task.credit.service.entities.LoanApplication;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

public class DataForIntegrationTests {

    public static Client client1() {
        return Client.builder()
                .familyName("Ivanov")
                .name("Ivan")
                .patronymic("Ivanovich")
                .passportDetails("1234567890")
                .maritalStatus("Single")
                .registration("Moscow, Red Square, 1")
                .contactNumber("89991234567")
                .employmentDetails("Engineer")
                .loanAgreementList(Collections.emptyList())
                .loanApplicationList(Collections.emptyList())
                .build();
    }

    public static Client client2() {
        return Client.builder()
                .familyName("Test")
                .name("Test")
                .patronymic("Test")
                .passportDetails("1111000000")
                .maritalStatus("Single")
                .registration("Moscow, Book, 1")
                .contactNumber("89991234567")
                .employmentDetails("Engineer")
                .loanAgreementList(Collections.emptyList())
                .loanApplicationList(Collections.emptyList())
                .build();
    }

    public static LoanApplicationRequestDto requestDto1() {
        return LoanApplicationRequestDto.builder()
                .familyName("Ivanov")
                .name("Ivan")
                .patronymic("Ivanovich")
                .passportDetails("1234567890")
                .maritalStatus("Single")
                .registration("Moscow, Red Square, 1")
                .contactNumber("89991234567")
                .employmentDetails("Engineer")
                .build();
    }

    public static LoanApplication loanApplication1() {
        return LoanApplication.builder()
                .isApprovedLoan(false)
                .loanAmount(new BigDecimal(10000))
                .loanTermMonths(1)
                .build();
    }

    public static LoanApplication loanApplication2() {
        return LoanApplication.builder()
                .isApprovedLoan(false)
                .loanAmount(new BigDecimal(30000))
                .loanTermMonths(4)
                .build();
    }

    public static LoanAgreement loanAgreement1() {
        return LoanAgreement.builder()
                .isSigned(false)
                .dateSigning(LocalDateTime.now())
                .build();
    }

    public static LoanAgreement loanAgreement2() {
        return LoanAgreement.builder()
                .isSigned(true)
                .dateSigning(LocalDateTime.now())
                .build();
    }

    public static ClientPersonalDataRequestDto personalDataRequestDto() {
        return ClientPersonalDataRequestDto.builder()
                .familyName("Ivanov")
                .name("Ivan")
                .patronymic("Ivanovich")
                .passportDetails("1234567890")
                .contactNumber("89991234567")
                .build();
    }
}
