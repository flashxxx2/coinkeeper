package payments.entity;

import lombok.Data;
import payments.validator.TaskValidBeanConstraint;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name = "payment_category")
@TaskValidBeanConstraint // можно валидировать целиком объект
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 5, max = 20)
    @NotBlank(message = "Category name is mandatory")
    private String name;
}
