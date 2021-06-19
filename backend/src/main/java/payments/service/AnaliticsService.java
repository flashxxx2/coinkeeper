package payments.service;

import org.springframework.stereotype.Service;
import payments.dto.AnaliticsDto;
import payments.entity.AnaliticsEntity;
import payments.mapper.Mapper;
import payments.repository.AnaliticsRepository;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AnaliticsService {

    private final AnaliticsRepository repository;

    public AnaliticsService(AnaliticsRepository repository) {
        this.repository = repository;
    }

    public AnaliticsDto getUserAnalitics(String userName) {

        return Mapper.toDtoAnalitics(repository.getAnaliticsEntitiesByUserName(userName).orElse(new AnaliticsEntity(
                null,
                userName,
                0L,
                0L,
                0L,
                BigDecimal.ZERO,
                "Неопределенная категория")));
    }

    public AnaliticsEntity save(AnaliticsDto analiticsDto) {
        return repository.save(Mapper.toEntityAnalitics(analiticsDto));
    }

    public AnaliticsEntity update(AnaliticsDto analiticsDto) {
        final var entity = repository.getAnaliticsEntitiesById(analiticsDto.getId());
        entity.setPlannedConsumption(analiticsDto.getPlannedConsumption());
        entity.setBalance(analiticsDto.getBalance());
        entity.setExpensivePurchase(analiticsDto.getExpensivePurchase());
        entity.setFactConsumption(analiticsDto.getFactConsumption());
        entity.setConsumptionCategory(analiticsDto.getConsumptionCategory());
        return repository.save(entity);
    }
}
