package payments.entity;

import lombok.*;
import org.springframework.data.domain.Page;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "files")
public class FileUploadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    private String url;

    @Column(name = "file_name")
    private String fileName;

    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="payment_id")
    private PaymentEntity payment;
}
