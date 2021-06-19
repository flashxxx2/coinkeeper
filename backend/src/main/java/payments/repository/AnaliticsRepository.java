package payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import payments.entity.AnaliticsEntity;

import java.util.Optional;

public interface AnaliticsRepository extends JpaRepository<AnaliticsEntity, Long> {


    @Query(value = "select * from analitics where user_name = :name", nativeQuery = true)
    Optional<AnaliticsEntity> getAnaliticsEntitiesByUserName(@Param("name") String userName);

    AnaliticsEntity getAnaliticsEntitiesById(Long id);
}
