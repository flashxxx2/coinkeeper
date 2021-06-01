package payments.entity;

import lombok.*;
import org.springframework.data.domain.Page;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "files")
public class MediaUploadEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "web_path")
    private String webPath;

    @Column
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne
    @JoinColumn(name = "payment_id")
    private PaymentEntity paymentEntity;
}
