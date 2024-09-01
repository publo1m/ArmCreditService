package ru.melnikov.task.credit.service.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.melnikov.task.credit.service.data.DataForIntegrationTests;
import ru.melnikov.task.credit.service.dto.request.ClientPersonalDataRequestDto;
import ru.melnikov.task.credit.service.dto.response.ClientResponseDto;
import ru.melnikov.task.credit.service.dto.response.LoanAgreementResponseDto;
import ru.melnikov.task.credit.service.dto.response.LoanApplicationResponseDto;
import ru.melnikov.task.credit.service.entities.Client;
import ru.melnikov.task.credit.service.entities.LoanAgreement;
import ru.melnikov.task.credit.service.entities.LoanApplication;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SystemManagementRepositoryTest {

    private SystemManagementRepository systemManagementRepository;
    private CreditResolverRepository creditResolverRepository;

    @BeforeEach
    void setUp() {
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        systemManagementRepository = new SystemManagementRepository(sessionFactory);
        creditResolverRepository = new CreditResolverRepository(sessionFactory);
    }

    @Test
    void testFindClientByClientPersonalDataRequestDto_shouldReturnClient() {
        Client client1 = DataForIntegrationTests.client1();
        ClientPersonalDataRequestDto requestDto = DataForIntegrationTests.personalDataRequestDto();

        creditResolverRepository.insertClient(client1);
        systemManagementRepository.findClientByClientPersonalDataRequestDto(requestDto);

        List<ClientResponseDto> clients = systemManagementRepository.findClientByClientPersonalDataRequestDto(requestDto);

        assertNotNull(clients);
        assertFalse(clients.isEmpty());
        ClientResponseDto clientResponse = clients.get(0);
        assertEquals(client1.getFamilyName(), clientResponse.getFamilyName());
        assertEquals(client1.getPassportDetails(), clientResponse.getPassportDetails());
    }

    @Test
    void testGetAllClients_shouldReturnClientList() {
        Client client1 = DataForIntegrationTests.client1();
        Client client2 = DataForIntegrationTests.client2();

        creditResolverRepository.insertClient(client1);
        creditResolverRepository.insertClient(client2);

        systemManagementRepository.getAllClients(0, 10);

        List<ClientResponseDto> clients = systemManagementRepository.getAllClients(0, 10);

        assertNotNull(clients);
        assertFalse(clients.isEmpty());
        assertTrue(clients.size() <= 10);
        assertEquals(client1.getFamilyName(), clients.get(0).getFamilyName());
        assertEquals(client2.getFamilyName(), clients.get(1).getFamilyName());
    }

    @Test
    void testGetAllAgreements_shouldReturnAgreementList() {
        LoanAgreement loanAgreement1 = DataForIntegrationTests.loanAgreement1();
        LoanAgreement loanAgreement2 = DataForIntegrationTests.loanAgreement2();

        Client client1 = creditResolverRepository.insertClient(DataForIntegrationTests.client1());
        Client client2 = creditResolverRepository.insertClient(DataForIntegrationTests.client2());

        LoanApplication loanApplication1 = creditResolverRepository
                .insertLoanApplication(DataForIntegrationTests.loanApplication1(), client1);

        LoanApplication loanApplication2 = creditResolverRepository
                .insertLoanApplication(DataForIntegrationTests.loanApplication2(), client2);

        creditResolverRepository.insertLoanAgreement(loanAgreement1, client1, loanApplication1);
        creditResolverRepository.insertLoanAgreement(loanAgreement2, client2, loanApplication2);

        systemManagementRepository.getAllAgreements(0, 10);

        List<LoanAgreementResponseDto> agreements = systemManagementRepository.getAllAgreements(0, 10);

        assertNotNull(agreements);
        assertFalse(agreements.isEmpty());
        assertTrue(agreements.size() <= 10);
        assertEquals(loanAgreement1.getLoanApplication().getLoanTermMonths(), agreements.get(0).getLoanTermMonths());
        assertEquals(loanAgreement2.getLoanApplication().getLoanTermMonths(), agreements.get(1).getLoanTermMonths());
    }

    @Test
    void testGetAllApplications_shouldReturnApplicationList() {
        LoanApplication loanApplication1 = DataForIntegrationTests.loanApplication1();
        LoanApplication loanApplication2 = DataForIntegrationTests.loanApplication2();

        Client client1 = creditResolverRepository.insertClient(DataForIntegrationTests.client1());
        Client client2 = creditResolverRepository.insertClient(DataForIntegrationTests.client2());

        creditResolverRepository.insertLoanApplication(loanApplication1, client1);
        creditResolverRepository.insertLoanApplication(loanApplication2, client2);

        systemManagementRepository.getAllApplications(0, 10);

        List<LoanApplicationResponseDto> applications = systemManagementRepository.getAllApplications(0, 10);

        assertNotNull(applications);
        assertFalse(applications.isEmpty());
        assertTrue(applications.size() <= 10);
        assertEquals(loanApplication1.getLoanAmount().intValue(), applications.get(0).getLoanAmount().intValue());
        assertEquals(loanApplication2.getLoanAmount().intValue(), applications.get(1).getLoanAmount().intValue());
    }

    @Test
    void testGetTotalApplicationsCount_shouldReturnCorrectTotalApplicationCount() {
        LoanApplication loanApplication1 = DataForIntegrationTests.loanApplication1();
        LoanApplication loanApplication2 = DataForIntegrationTests.loanApplication2();

        Client client1 = creditResolverRepository.insertClient(DataForIntegrationTests.client1());
        Client client2 = creditResolverRepository.insertClient(DataForIntegrationTests.client2());

        creditResolverRepository.insertLoanApplication(loanApplication1, client1);
        creditResolverRepository.insertLoanApplication(loanApplication2, client2);

        long count = systemManagementRepository.getTotalApplicationsCount();

        assertEquals(2, count);
    }

    @Test
    void testGetTotalAgreementsCount_shouldReturnCorrectTotalAgreementCount() {
        LoanAgreement loanAgreement1 = DataForIntegrationTests.loanAgreement1();
        LoanAgreement loanAgreement2 = DataForIntegrationTests.loanAgreement2();

        Client client1 = creditResolverRepository.insertClient(DataForIntegrationTests.client1());
        Client client2 = creditResolverRepository.insertClient(DataForIntegrationTests.client2());

        LoanApplication loanApplication1 = creditResolverRepository
                .insertLoanApplication(DataForIntegrationTests.loanApplication1(), client1);

        LoanApplication loanApplication2 = creditResolverRepository
                .insertLoanApplication(DataForIntegrationTests.loanApplication2(), client2);

        creditResolverRepository.insertLoanAgreement(loanAgreement1, client1, loanApplication1);
        creditResolverRepository.insertLoanAgreement(loanAgreement2, client2, loanApplication2);

        long count = systemManagementRepository.getTotalAgreementsCount();

        assertEquals(2, count);
    }

    @Test
    void testGetTotalClientsCount_shouldReturnCorrectTotalClientCount() {
        Client client1 = DataForIntegrationTests.client1();
        Client client2 = DataForIntegrationTests.client2();

        creditResolverRepository.insertClient(client1);
        creditResolverRepository.insertClient(client2);

        long count = systemManagementRepository.getTotalClientsCount();

        assertEquals(2, count);
    }
}