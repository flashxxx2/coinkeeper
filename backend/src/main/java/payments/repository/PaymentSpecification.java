package payments.repository;

import org.springframework.data.jpa.domain.Specification;
import payments.models.PaymentEntity;

import java.time.LocalDateTime;

public class PaymentSpecification {

    public static Specification<PaymentEntity> paymentByCategory(final Long categoryId) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("categoryEntity").get("id"), categoryId);
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

    public static Specification<PaymentEntity> getPaymentCriteria(Long categoryId, LocalDateTime after, LocalDateTime before) {
        Specification<PaymentEntity> where = trueSpecification();
        if (categoryId != null) {
            where = where.and(paymentByCategory(categoryId));
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