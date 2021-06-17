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

    public AnaliticsDto getUserAnalitics(Long userId) {
        return Optional.of(Mapper.toDtoAnalitics(repository.getAnaliticsEntitiesByUserId(userId))).orElse(new AnaliticsDto(
                null,
                0L,
                0L,
                0L,
                BigDecimal.ZERO,
                "Неопределенная категория"));
    }

    public AnaliticsEntity save(AnaliticsDto analiticsDto) {
        return repository.save(Mapper.toEntityAnalitics(analiticsDto));
    }

    public AnaliticsEntity update(AnaliticsDto analiticsDto) {
        final var entity = repository.getAnaliticsEntitiesByUserId(analiticsDto.getId());
        entity.setPlannedConsumption(analiticsDto.getPlannedConsumption());
        entity.setBalance(analiticsDto.getBalance());
        entity.setExpensivePurchase(analiticsDto.getExpensivePurchase());
        entity.setFactConsumption(analiticsDto.getFactConsumption());
        entity.setConsumptionCategory(analiticsDto.getConsumptionCategory());
        entity.setUserId(1L);
        return repository.save(entity);
    }
}
