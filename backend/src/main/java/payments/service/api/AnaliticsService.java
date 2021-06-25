package payments.service.api;

import payments.dto.AnaliticsCategoryDto;
import payments.dto.AnaliticsConsumptionDto;
import payments.dto.AnaliticsDto;
import payments.entity.AnaliticsEntity;

import java.util.List;

public interface AnaliticsService {

    AnaliticsDto getUserAnalitics(String userName);

    AnaliticsEntity save(AnaliticsDto analiticsDto);

    AnaliticsEntity update(AnaliticsDto analiticsDto, String userName);

    List<AnaliticsConsumptionDto> getConsumptionGraph(String userName);

    List<AnaliticsCategoryDto> getCategoryGraph(String userName);

}
