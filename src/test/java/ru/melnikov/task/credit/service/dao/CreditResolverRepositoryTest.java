package ru.melnikov.task.credit.service.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.melnikov.task.credit.service.data.DataForIntegrationTests;
import ru.melnikov.task.credit.service.dto.request.LoanApplicationRequestDto;
import ru.melnikov.task.credit.service.entities.Client;
import ru.melnikov.task.credit.service.entities.LoanAgreement;
import ru.melnikov.task.credit.service.entities.LoanApplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreditResolverRepositoryTest {

    private SessionFactory sessionFactory;
    private CreditResolverRepository creditResolverRepository;

    @BeforeEach
    void setUp() {
        sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        creditResolverRepository = new CreditResolverRepository(sessionFactory);
    }

    @Test
    void testInsertTwoClient_shouldReturnTwoElements() {
        Client client1 = DataForIntegrationTests.client1();
        Client client2 = DataForIntegrationTests.client2();

        creditResolverRepository.insertClient(client1);
        creditResolverRepository.insertClient(client2);

        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Client> root = cq.from(Client.class);

        cq.select(cb.count(root));
        Query<Long> query = session.createQuery(cq);

        assertEquals(2, query.getSingleResult());
    }

    @Test
    void testInsertTwoLoanApplication_shouldReturnTwoElements() {
        LoanApplication loanApplication1 = DataForIntegrationTests.loanApplication1();
        LoanApplication loanApplication2 = DataForIntegrationTests.loanApplication2();

        Client client1 = creditResolverRepository.insertClient(DataForIntegrationTests.client1());
        Client client2 = creditResolverRepository.insertClient(DataForIntegrationTests.client2());

        creditResolverRepository.insertLoanApplication(loanApplication1, client1);
        creditResolverRepository.insertLoanApplication(loanApplication2, client2);

        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Client> root = cq.from(Client.class);

        cq.select(cb.count(root));
        Query<Long> query = session.createQuery(cq);

        assertEquals(2, query.getSingleResult());
    }

    @Test
    void testInsertTwoLoanAgreement_shouldReturnTwoElements() {
        LoanAgreement loanAgreement1 = DataForIntegrationTests.loanAgreement1();
        LoanAgreement loanAgreement2 = DataForIntegrationTests.loanAgreement2();

        Client client1 = creditResolverRepository.insertClient(DataForIntegrationTests.client1());
        Client client2 = creditResolverRepository.insertClient(DataForIntegrationTests.client2());

        LoanApplication loanApplication1 = creditResolverRepository
                .insertLoanApplication(DataForIntegrationTests.loanApplication1(), client1);

        LoanApplication loanApplication2 = creditResolverRepository
                .insertLoanApplication(DataForIntegrationTests.loanApplication2(), client2);

        creditResolverRepository.insertLoanApplication(loanApplication1, client1);
        creditResolverRepository.insertLoanApplication(loanApplication2, client2);


        creditResolverRepository.insertLoanAgreement(loanAgreement1, client1, loanApplication1);
        creditResolverRepository.insertLoanAgreement(loanAgreement2, client2, loanApplication2);

        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Client> root = cq.from(Client.class);

        cq.select(cb.count(root));
        Query<Long> query = session.createQuery(cq);

        assertEquals(2, query.getSingleResult());
    }

    @Test
    void testUpdateStatusApplication_shouldReturnApplicationWithApprovedLoanTrue() {
        LoanApplication loanApplication1 = DataForIntegrationTests.loanApplication1();

        Client client1 = creditResolverRepository.insertClient(DataForIntegrationTests.client1());

        LoanApplication existApplication = creditResolverRepository.insertLoanApplication(loanApplication1, client1);
        creditResolverRepository.updateStatusApplication(existApplication.getLoanApplicationId());
        Session session = sessionFactory.openSession();

        assertTrue(session.get(LoanApplication.class, existApplication.getLoanApplicationId()).isApprovedLoan());
    }

    @Test
    void testSignAgreement_shouldReturnLoanAgreementWithSignedTrue() {
        LoanAgreement loanAgreement1 = DataForIntegrationTests.loanAgreement1();

        Client client1 = creditResolverRepository.insertClient(DataForIntegrationTests.client1());
        LoanApplication loanApplication1 = creditResolverRepository
                .insertLoanApplication(DataForIntegrationTests.loanApplication1(), client1);

        LoanAgreement existLoanAgreement = creditResolverRepository.insertLoanAgreement(loanAgreement1, client1, loanApplication1);
        creditResolverRepository.signAgreement(existLoanAgreement.getLoanAgreementId());
        Session session = sessionFactory.openSession();

        assertTrue(session.get(LoanAgreement.class, existLoanAgreement.getLoanAgreementId()).isSigned());
    }

    @Test
    void testDeclineSignAgreement_shouldReturnLoanAgreementWithSignedFalse() {
        LoanAgreement loanAgreement2 = DataForIntegrationTests.loanAgreement2();

        Client client2 = creditResolverRepository.insertClient(DataForIntegrationTests.client2());
        LoanApplication loanApplication2 = creditResolverRepository
                .insertLoanApplication(DataForIntegrationTests.loanApplication2(), client2);

        LoanAgreement existLoanAgreement = creditResolverRepository.insertLoanAgreement(loanAgreement2, client2, loanApplication2);
        creditResolverRepository.declineAgreement(existLoanAgreement.getLoanAgreementId());
        Session session = sessionFactory.openSession();

        assertFalse(session.get(LoanAgreement.class, existLoanAgreement.getLoanAgreementId()).isSigned());
    }

    @Test
    void testFindClientByLoanApplicationRequestDto_shouldReturnClient() {
        Client client1 = DataForIntegrationTests.client1();
        LoanApplicationRequestDto requestDto1 = DataForIntegrationTests.requestDto1();

        creditResolverRepository.insertClient(client1);
        Client expectClient = creditResolverRepository.findClientByLoanApplicationRequestDto(requestDto1);

        assertEquals(client1.getFamilyName(), expectClient.getFamilyName());
        assertEquals(client1.getPassportDetails(), expectClient.getPassportDetails());
    }
}