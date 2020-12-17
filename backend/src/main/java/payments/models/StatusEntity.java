package payments.models;

import lombok.Data;

import javax.persistence.*;

@Table(name = "payment_statuses")
@Entity
@Data
public class StatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
