package payments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnaliticsConsumptionDto {
    private Long id;
    private String name;
    private Long consumptionInMonth;


}
