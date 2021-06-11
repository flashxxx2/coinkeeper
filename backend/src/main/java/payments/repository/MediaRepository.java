package payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import payments.entity.FileUploadEntity;
import payments.entity.PaymentEntity;

import java.util.List;

public interface MediaRepository extends JpaRepository<FileUploadEntity, Long>{

    List<FileUploadEntity> findAllByPayment(PaymentEntity entity);

    @Modifying
    @Query(value = "delete from files where payment_id = :id", nativeQuery = true)
    void deleteByPaymentId(@Param("id") Long id);

    @Query(value = "delete from files where payment_id IS NULL", nativeQuery = true)
    void deleteUnMappedRows();
}
