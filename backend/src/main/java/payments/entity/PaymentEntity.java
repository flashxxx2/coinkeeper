package payments.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import payments.dto.MediaUploadDto;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "payment_statistic")
@Entity
@Data
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty
    @Column(name = "created_dt")
    private LocalDateTime createdTime;

    @JsonProperty
    @Column
    private String comment;

    @JsonProperty
    @Column(precision = 12, scale = 2)
    private BigDecimal sum;

    @JsonProperty
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @ToString.Exclude
    @JsonProperty
    @OneToOne(mappedBy="paymentEntity")
    private MediaUploadEntity mediaUploadEntity;

}
