package payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import payments.entity.MediaUploadEntity;

import java.util.Optional;
import java.util.UUID;

public interface MediaRepository extends JpaRepository<MediaUploadEntity, UUID> {
    @Query(value = "select * from files where id = :id", nativeQuery = true)
    Optional<MediaUploadEntity> getById(@Param("id") UUID id);
}
