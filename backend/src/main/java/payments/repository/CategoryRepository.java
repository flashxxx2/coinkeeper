package payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import payments.models.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
