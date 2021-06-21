package payments.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import payments.entity.PaymentEntity;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long>, JpaSpecificationExecutor<PaymentEntity> {

    PaymentEntity getById(Long id);

    @Query(value = "select category_id from payment_statistic where sum = (select max(sum) from payment_statistic where user_name = :name and extract\n" +
            "    (month FROM created_dt) = extract (month FROM CURRENT_DATE))", nativeQuery = true)
    Long getMostExpensiveCategory(@Param("name") String userName);

    @Query(value = "select max(sum) from payment_statistic where user_name = :name and extract (month FROM created_dt) = extract (month FROM CURRENT_DATE)", nativeQuery = true)
    Long getMostExpensivePurchase(@Param("name") String userName);

    @Query(value = "select * from payment_statistic where user_name = :user and extract (month FROM created_dt) = extract (month FROM CURRENT_DATE)", nativeQuery = true)
    List<PaymentEntity> findAllByUserName(@Param("user") String userName);
}
