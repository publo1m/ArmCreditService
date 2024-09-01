package ru.melnikov.task.credit.service.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import ru.melnikov.task.credit.service.dto.request.LoanApplicationRequestDto;
import ru.melnikov.task.credit.service.entities.Client;
import ru.melnikov.task.credit.service.entities.LoanAgreement;
import ru.melnikov.task.credit.service.entities.LoanApplication;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreditResolverRepository {

    private final SessionFactory sessionFactory;

    public Client insertClient(Client client) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Client existClient = session.merge(client);

            transaction.commit();
            return existClient;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public LoanApplication insertLoanApplication(LoanApplication loanApplication, Client client) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            loanApplication.setClient(client);
            client.getLoanApplicationList().add(loanApplication);
            LoanApplication existLoanApplication = session.merge(loanApplication);

            transaction.commit();
            return existLoanApplication;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public LoanAgreement insertLoanAgreement(LoanAgreement loanAgreement, Client client, LoanApplication loanApplication) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            loanAgreement.setClient(client);
            loanAgreement.setLoanApplication(loanApplication);
            LoanAgreement existLoanAgreement = session.merge(loanAgreement);
            client.getLoanAgreementList().add(existLoanAgreement);

            transaction.commit();
            return existLoanAgreement;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void updateStatusApplication(UUID applicationId) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaUpdate<LoanApplication> update = cb.createCriteriaUpdate(LoanApplication.class);
            Root<LoanApplication> root = update.from(LoanApplication.class);

            update.set("isApprovedLoan", true);
            update.where(cb.equal(root.get("loanApplicationId"), applicationId));

            session.createMutationQuery(update).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void signAgreement(UUID agreementId) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaUpdate<LoanAgreement> update = cb.createCriteriaUpdate(LoanAgreement.class);
            Root<LoanAgreement> root = update.from(LoanAgreement.class);

            update.set("isSigned", true);
            update.set("dateSigning", LocalDateTime.now());
            update.where(cb.equal(root.get("loanAgreementId"), agreementId));

            session.createMutationQuery(update).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void declineAgreement(UUID agreementId) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaUpdate<LoanAgreement> update = cb.createCriteriaUpdate(LoanAgreement.class);
            Root<LoanAgreement> root = update.from(LoanAgreement.class);

            update.set("isSigned", false);
            update.set("dateSigning", LocalDateTime.now());
            update.where(cb.equal(root.get("loanAgreementId"), agreementId));

            session.createMutationQuery(update).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public Client findClientByLoanApplicationRequestDto(LoanApplicationRequestDto requestDto) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Client> cq = cb.createQuery(Client.class);
        Root<Client> root = cq.from(Client.class);

        cq.select(root)
                .where(cb.equal(root.get("familyName"), requestDto.getFamilyName()),
                        cb.equal(root.get("name"), requestDto.getName()),
                        cb.equal(root.get("patronymic"), requestDto.getPatronymic()),
                        cb.equal(root.get("passportDetails"), requestDto.getPassportDetails()),
                        cb.equal(root.get("contactNumber"), requestDto.getContactNumber()));

        Query<Client> query = session.createQuery(cq);

        return query.uniqueResult();
    }
}