package payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import payments.entity.AnaliticsEntity;

public interface AnaliticsRepository extends JpaRepository<AnaliticsEntity, Long> {


    @Query(value = "select * from analitics where user_id = :id", nativeQuery = true)
    AnaliticsEntity getAnaliticsEntitiesByUserId(@Param("id") Long userId);
}
