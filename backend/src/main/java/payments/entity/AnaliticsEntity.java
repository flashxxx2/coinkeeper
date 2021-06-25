package payments.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "analitics")
public class AnaliticsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty
    @Column(name = "user_name")
    private String userName;

    @JsonProperty
    private Long balance;

    @JsonProperty
    @Column(name = "planned_consumption")
    private Long plannedConsumption;

    @JsonProperty
    @Column(name = "fact_consumption")
    private Long factConsumption;

    @JsonProperty
    @Column(name = "expensive_purchase")
    private Long expensivePurchase;

    @JsonProperty
    @Column(name = "consumption_category")
    @NotBlank(message = "Category is mandatory")
    private String consumptionCategory;
}
