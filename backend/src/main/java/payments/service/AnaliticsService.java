package payments.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import payments.dto.AnaliticsDto;
import payments.entity.AnaliticsEntity;
import payments.entity.PaymentEntity;
import payments.mapper.Mapper;
import payments.repository.AnaliticsRepository;
import payments.repository.CategoryRepository;
import payments.repository.PaymentRepository;

import java.time.LocalDateTime;

@Service
@Transactional
public class AnaliticsService {

    private final AnaliticsRepository repository;
    private final PaymentRepository paymentRepository;
    private final CategoryRepository categoryRepository;
    private static final String NO_OPERATION = "У вас нет операций с расходами";

    public AnaliticsService(AnaliticsRepository repository, PaymentRepository paymentRepository, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.paymentRepository = paymentRepository;
        this.categoryRepository = categoryRepository;
    }

    public AnaliticsDto getUserAnalitics(String userName) {
        final var analiticsEntity = repository.getAnaliticsEntitiesByUserName(userName);
        if (analiticsEntity.isPresent()) {
            final var sum = paymentRepository.findAllByUserName(userName)
                    .stream()
                    .mapToLong(PaymentEntity::getSum)
                    .sum();
            analiticsEntity.ifPresent(entity -> entity.setFactConsumption(sum));

            Long mostExpensivePurchase = paymentRepository.getMostExpensivePurchase(userName);
            if (mostExpensivePurchase != null) {
                analiticsEntity.ifPresent(entity -> entity.setExpensivePurchase(paymentRepository.getMostExpensivePurchase(userName)));

                var category = categoryRepository.getOne(paymentRepository.getMostExpensiveCategory(userName));
                analiticsEntity.ifPresent(entity -> entity.setConsumptionCategory(category.getName()));
            } else {
                analiticsEntity.ifPresent(entity -> entity.setExpensivePurchase(0L));
                analiticsEntity.ifPresent(entity -> entity.setConsumptionCategory(NO_OPERATION));
            }
        }

        return Mapper.toDtoAnalitics(analiticsEntity.orElse(new AnaliticsEntity(
                repository.getMaxId(),
                userName,
                0L,
                0L,
                0L,
                0L,
                "Неопределенная категория")));
    }

    public AnaliticsEntity save(AnaliticsDto analiticsDto) {
        return repository.save(Mapper.toEntityAnalitics(analiticsDto));
    }

    public AnaliticsEntity update(AnaliticsDto analiticsDto, String userName) {
        var entity = repository.getAnaliticsEntitiesById(analiticsDto.getId());
        if (entity == null) {
            entity = new AnaliticsEntity();
        }
        entity.setPlannedConsumption(analiticsDto.getPlannedConsumption());
        entity.setBalance(analiticsDto.getBalance());
        entity.setExpensivePurchase(analiticsDto.getExpensivePurchase());
        entity.setFactConsumption(analiticsDto.getFactConsumption());
        entity.setUserName(userName);
        entity.setId(analiticsDto.getId());
        entity.setConsumptionCategory(analiticsDto.getConsumptionCategory());
        return repository.save(entity);
    }
}
