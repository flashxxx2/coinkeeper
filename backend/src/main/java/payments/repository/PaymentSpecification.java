package payments.repository;

import org.springframework.data.jpa.domain.Specification;
import payments.models.PaymentEntity;

import java.time.LocalDateTime;

public class PaymentSpecification {
    public static Specification<PaymentEntity> paymentByStatus(final Long statusId) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("statusEntity").get("id"), statusId);
    }

    public static Specification<PaymentEntity> paymentCreateBefore(final LocalDateTime before) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("createdTime"), before);
    }

    public static Specification<PaymentEntity> paymentCreateAfter(final LocalDateTime after) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("createdTime"), after);
    }

    public static Specification<PaymentEntity> trueSpecification () {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and();
    }

    public static Specification<PaymentEntity> getPaymentCriteria(Long statusId, LocalDateTime after, LocalDateTime before) {
        Specification<PaymentEntity> where = trueSpecification();
        if (statusId != null) {
            where = where.and(paymentByStatus(statusId));
        }
        if (after != null) {
            where = where.and(paymentCreateAfter(after));
        }
        if (before != null) {
            where = where.and(paymentCreateBefore(before));
        }
        return where;
    }
}