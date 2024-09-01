package ru.melnikov.task.credit.service.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import ru.melnikov.task.credit.service.dto.request.ClientPersonalDataRequestDto;
import ru.melnikov.task.credit.service.dto.response.ClientResponseDto;
import ru.melnikov.task.credit.service.dto.response.LoanAgreementResponseDto;
import ru.melnikov.task.credit.service.dto.response.LoanApplicationResponseDto;
import ru.melnikov.task.credit.service.entities.Client;
import ru.melnikov.task.credit.service.entities.LoanAgreement;
import ru.melnikov.task.credit.service.entities.LoanApplication;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SystemManagementRepository {

    private final SessionFactory sessionFactory;

    public List<ClientResponseDto> findClientByClientPersonalDataRequestDto(ClientPersonalDataRequestDto requestDto) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<ClientResponseDto> cq = cb.createQuery(ClientResponseDto.class);
            Root<Client> root = cq.from(Client.class);

            cq.select(cb.construct(
                    ClientResponseDto.class,
                    root.get("familyName"),
                    root.get("name"),
                    root.get("patronymic"),
                    root.get("passportDetails"),
                    root.get("contactNumber"),
                    root.get("maritalStatus"),
                    root.get("registration"),
                    root.get("employmentDetails")
            ));

            List<Predicate> predicates = new ArrayList<>();

            if (requestDto.getFamilyName() != null && !requestDto.getFamilyName().isEmpty()) {
                predicates.add(cb.like(root.get("familyName"), "%" + requestDto.getFamilyName() + "%"));
            }
            if (requestDto.getName() != null && !requestDto.getName().isEmpty()) {
                predicates.add(cb.like(root.get("name"), "%" + requestDto.getName() + "%"));
            }
            if (requestDto.getPatronymic() != null && !requestDto.getPatronymic().isEmpty()) {
                predicates.add(cb.like(root.get("patronymic"), "%" + requestDto.getPatronymic() + "%"));
            }
            if (requestDto.getPassportDetails() != null && !requestDto.getPassportDetails().isEmpty()) {
                predicates.add(cb.like(root.get("passportDetails"), "%" + requestDto.getPassportDetails() + "%"));
            }
            if (requestDto.getContactNumber() != null && !requestDto.getContactNumber().isEmpty()) {
                predicates.add(cb.like(root.get("contactNumber"), "%" + requestDto.getContactNumber() + "%"));
            }

            cq.where(predicates.toArray(new Predicate[0]));
            Query<ClientResponseDto> query = session.createQuery(cq);

            return query.getResultList();
        }
    }

    public List<ClientResponseDto> getAllClients(int page, int size) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<ClientResponseDto> cq = cb.createQuery(ClientResponseDto.class);
            Root<Client> root = cq.from(Client.class);

            cq.select(cb.construct(ClientResponseDto.class,
                    root.get("familyName"),
                    root.get("name"),
                    root.get("patronymic"),
                    root.get("passportDetails"),
                    root.get("contactNumber"),
                    root.get("maritalStatus"),
                    root.get("registration"),
                    root.get("employmentDetails")));

            Query<ClientResponseDto> query = session.createQuery(cq);
            query.setFirstResult(page * size);
            query.setMaxResults(size);

            return query.getResultList();
        }
    }

    public List<LoanAgreementResponseDto> getAllAgreements(int page, int size) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<LoanAgreementResponseDto> cq = cb.createQuery(LoanAgreementResponseDto.class);
            Root<LoanAgreement> root = cq.from(LoanAgreement.class);

            Join<LoanAgreement, LoanApplication> loanApplicationJoin = root.join("loanApplication", JoinType.LEFT);
            cq.select(cb.construct(LoanAgreementResponseDto.class,
                    root.get("loanAgreementId"),
                    root.get("isSigned"),
                    root.get("dateSigning"),
                    loanApplicationJoin.get("loanAmount"),
                    loanApplicationJoin.get("loanTermMonths")));

            Query<LoanAgreementResponseDto> query = session.createQuery(cq);
            query.setFirstResult(page * size);
            query.setMaxResults(size);

            return query.getResultList();
        }
    }

    public List<LoanApplicationResponseDto> getAllApplications(int page, int size) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<LoanApplicationResponseDto> cq = cb.createQuery(LoanApplicationResponseDto.class);
            Root<LoanApplication> root = cq.from(LoanApplication.class);

            cq.select(cb.construct(LoanApplicationResponseDto.class,
                    root.get("loanApplicationId"),
                    root.get("isApprovedLoan"),
                    root.get("loanAmount"),
                    root.get("loanTermMonths")));

            Query<LoanApplicationResponseDto> query = session.createQuery(cq);
            query.setFirstResult(page * size);
            query.setMaxResults(size);

            return query.getResultList();
        }
    }

    public long getTotalApplicationsCount() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<LoanApplication> root = cq.from(LoanApplication.class);

            cq.select(cb.count(root));
            Query<Long> query = session.createQuery(cq);

            return query.getSingleResult();
        }
    }

    public long getTotalAgreementsCount() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<LoanAgreement> root = cq.from(LoanAgreement.class);

            cq.select(cb.count(root));
            Query<Long> query = session.createQuery(cq);

            return query.getSingleResult();
        }
    }

    public long getTotalClientsCount() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Client> root = cq.from(Client.class);

            cq.select(cb.count(root));
            Query<Long> query = session.createQuery(cq);

            return query.getSingleResult();
        }
    }
}
