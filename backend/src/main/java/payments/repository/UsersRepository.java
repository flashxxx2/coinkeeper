package payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import payments.entity.UserEntity;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "select * from users where username = :username", nativeQuery = true)
    Optional<UserEntity> findByUsername(@Param("username") String username);
}
