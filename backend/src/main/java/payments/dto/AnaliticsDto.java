package payments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnaliticsDto {
    @Min(1)
    private Long id;

    @NotNull
    @Size()
    private Long balance;

    @NotNull
    @Size()
    private Long plannedConsumption;

    @NotNull
    @Size()
    private Long factConsumption;

    @NotNull
    @Size()
    private Long expensivePurchase;

    @NotNull
    @Size()
    private String consumptionCategory;
}
