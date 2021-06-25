package payments.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import payments.entity.PaymentEntity;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long>, JpaSpecificationExecutor<PaymentEntity> {

    @Override
    Optional<PaymentEntity> findById(Long id);

    PaymentEntity getById(Long id);

    @Query(value = "select max(category_id) from payment_statistic where sum = (select max(sum) from payment_statistic where user_name = :name and extract(month FROM created_dt) = extract (month FROM CURRENT_DATE));", nativeQuery = true)
    Long getMostExpensiveCategory(@Param("name") String userName);

    @Query(value = "select max(sum) from payment_statistic where user_name = :name and extract (month FROM created_dt) = extract (month FROM CURRENT_DATE)", nativeQuery = true)
    Long getMostExpensivePurchase(@Param("name") String userName);

    @Query(value = "select * from payment_statistic where user_name = :user and extract (month FROM created_dt) = extract (month FROM CURRENT_DATE)", nativeQuery = true)
    List<PaymentEntity> findAllByUserName(@Param("user") String userName);

    @Query(value = "SELECT * FROM payment_statistic WHERE date_part('year', created_dt) = date_part('year', CURRENT_DATE) and user_name = :name", nativeQuery = true)
    List<PaymentEntity> findAllPaymentsInCurrentYear(@Param("name") String userName);

    Optional<PaymentEntity> getPaymentEntityById(Long id);
}
