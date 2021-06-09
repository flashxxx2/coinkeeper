package payments.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import payments.entity.PaymentEntity;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long>, JpaSpecificationExecutor<PaymentEntity> {
    PaymentEntity getById(Long id);
}
