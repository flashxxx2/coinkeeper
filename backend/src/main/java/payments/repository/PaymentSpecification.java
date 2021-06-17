package payments.repository;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import payments.criteria.PaymentCriteria;
import payments.entity.PaymentEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;

@AllArgsConstructor
public class PaymentSpecification implements Specification<PaymentEntity> {
    private PaymentCriteria criteria;

    @Override
    public Predicate toPredicate(Root<PaymentEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Predicate predicate = builder.and();
        if (criteria.getId() != null) {
            predicate = builder.and(predicate, builder.equal(root.get("id"), criteria.getId()));
        }
        if (criteria.getCreatedTimeFrom() != null) {
            predicate = builder.and(predicate,
                    builder.greaterThanOrEqualTo(root.get("createdTime").as(LocalDate.class),
                            criteria.getCreatedTimeFrom().toLocalDate()));
        }
        if (criteria.getCreatedTimeTo() != null) {
            predicate = builder.and(predicate, builder.lessThanOrEqualTo(root.get("createdTime").as(LocalDate.class),
                    criteria.getCreatedTimeTo().toLocalDate()));
        }
        if (criteria.getCategoryId() != null) {
            predicate = builder.and(predicate, builder.equal(root.get("category").get("id"),
                    criteria.getCategoryId()));
        }
        if (criteria.getUserId() != null) {
            predicate = builder.and(predicate, builder.equal(root.get("userId"),
                    criteria.getUserId()));
        }

        return predicate;
    }
}