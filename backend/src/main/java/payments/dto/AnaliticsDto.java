package payments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnaliticsDto {
    private Long id;
    private Long balance;
    private Long plannedConsumption;
    private Long factConsumption;
    private Long expensivePurchase;
    private String consumptionCategory;
}
