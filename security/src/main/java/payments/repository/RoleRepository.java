package payments.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import payments.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
