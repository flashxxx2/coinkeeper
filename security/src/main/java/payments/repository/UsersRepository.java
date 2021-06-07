package payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import payments.entity.UserEntity;

public interface UsersRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}
