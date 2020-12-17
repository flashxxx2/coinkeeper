package payments.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import payments.models.StatusEntity;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Long> {

}
