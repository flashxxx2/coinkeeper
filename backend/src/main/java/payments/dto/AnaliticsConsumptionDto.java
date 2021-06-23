package payments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnaliticsConsumptionDto {
    @Min(1)
    private Long id;
    private String name;

    @Size
    private Long consumptionInMonth;
}
