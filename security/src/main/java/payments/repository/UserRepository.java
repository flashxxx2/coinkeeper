package payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import payments.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
