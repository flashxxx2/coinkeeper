package payments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnaliticsCategoryDto {
    @Min(1)
    private Long id;
    private String name;
    private Long consumptionInCategory;
    private String toolTip;
}
