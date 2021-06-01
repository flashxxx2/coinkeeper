package payments.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "payment_category")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
