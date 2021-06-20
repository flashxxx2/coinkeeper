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

    @Query(value = "select planned_consumption from analitics where user_name = :name", nativeQuery = true)
    Long getUserPlannedConsumption(@Param("name") String userName);

    @Query(value = "select balance from analitics where user_name = :name", nativeQuery = true)
    Long getBalance(@Param("name") String userName);

    @Query(value = "select count(id) + 1 from analitics", nativeQuery = true)
    Long getMaxId();
}
