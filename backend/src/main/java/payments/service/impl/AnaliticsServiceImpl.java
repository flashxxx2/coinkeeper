package payments.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import payments.dto.AnaliticsCategoryDto;
import payments.dto.AnaliticsConsumptionDto;
import payments.dto.AnaliticsDto;
import payments.entity.AnaliticsEntity;
import payments.entity.PaymentEntity;
import payments.mapper.Mapper;
import payments.repository.AnaliticsRepository;
import payments.repository.CategoryRepository;
import payments.repository.PaymentRepository;
import payments.service.api.AnaliticsService;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Service
@Validated
@Transactional
public class AnaliticsServiceImpl implements AnaliticsService {

    private final AnaliticsRepository repository;
    private final PaymentRepository paymentRepository;
    private final CategoryRepository categoryRepository;
    private static final String NO_OPERATION = "У вас нет операций с расходами";
    private static final String UNDEFINED_CATEGORY = "Неопределенная категория";
    private static final String PRODUCTS = "Продукты";
    private static final String TRANSPORT = "Транспорт";
    private static final String FUNNY = "Развлечения";
    private static final String ROMANTIC = "Романтика";
    private static final String FOOD_OUT_HOME = "Еда вне дома";
    private static final String SERVICES = "Услуги";

    public AnaliticsServiceImpl(AnaliticsRepository repository, PaymentRepository paymentRepository, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.paymentRepository = paymentRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
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
                UNDEFINED_CATEGORY)));
    }

    @Override
    public AnaliticsEntity save(AnaliticsDto analiticsDto) {
        return repository.save(Mapper.toEntityAnalitics(analiticsDto));
    }

    @Override
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

    @Override
    public List<AnaliticsConsumptionDto> getConsumptionGraph(String userName) {
        final var payments = paymentRepository.findAllPaymentsInCurrentYear(userName);

        var sumInFebruary = payments.stream()
                .filter(paymentEntity -> paymentEntity.getCreatedTime().isAfter(LocalDateTime.of(LocalDateTime.now().getYear(), Month.FEBRUARY, 1, 0, 0))
                        && paymentEntity.getCreatedTime().isBefore(LocalDateTime.of(LocalDateTime.now().getYear(), Month.FEBRUARY, 28, 23, 59)))
                .mapToLong(PaymentEntity::getSum)
                .sum();

        List<AnaliticsConsumptionDto> listOfDto = new ArrayList<>();
        listOfDto.add(new AnaliticsConsumptionDto(1L, "January", getSumInLongMonth(payments, Month.JANUARY)));
        listOfDto.add(new AnaliticsConsumptionDto(2L, "February", sumInFebruary));
        listOfDto.add(new AnaliticsConsumptionDto(3L, "March", getSumInLongMonth(payments, Month.MARCH)));
        listOfDto.add(new AnaliticsConsumptionDto(4L, "April", getSumInShortMonth(payments, Month.APRIL)));
        listOfDto.add(new AnaliticsConsumptionDto(5L, "May", getSumInLongMonth(payments, Month.MAY)));
        listOfDto.add(new AnaliticsConsumptionDto(6L, "June", getSumInShortMonth(payments, Month.JUNE)));
        listOfDto.add(new AnaliticsConsumptionDto(7L, "July", getSumInLongMonth(payments, Month.JULY)));
        listOfDto.add(new AnaliticsConsumptionDto(8L, "August", getSumInLongMonth(payments, Month.AUGUST)));
        listOfDto.add(new AnaliticsConsumptionDto(9L, "September", getSumInShortMonth(payments, Month.SEPTEMBER)));
        listOfDto.add(new AnaliticsConsumptionDto(10L, "October", getSumInLongMonth(payments, Month.OCTOBER)));
        listOfDto.add(new AnaliticsConsumptionDto(11L, "November", getSumInShortMonth(payments, Month.NOVEMBER)));
        listOfDto.add(new AnaliticsConsumptionDto(12L, "December", getSumInLongMonth(payments, Month.DECEMBER)));

        return listOfDto;
    }

    @Override
    public List<AnaliticsCategoryDto> getCategoryGraph(String userName) {
        final var payments = paymentRepository.findAllPaymentsInCurrentYear(userName);

        List<AnaliticsCategoryDto> listOfDto = new ArrayList<>();
        listOfDto.add(new AnaliticsCategoryDto(1L, PRODUCTS, getSumInCategory(payments, PRODUCTS), "tooltip1"));
        listOfDto.add(new AnaliticsCategoryDto(2L, TRANSPORT, getSumInCategory(payments, TRANSPORT), "tooltip2"));
        listOfDto.add(new AnaliticsCategoryDto(3L, FUNNY, getSumInCategory(payments, FUNNY), "tooltip3"));
        listOfDto.add(new AnaliticsCategoryDto(4L, ROMANTIC, getSumInCategory(payments, ROMANTIC), "tooltip4"));
        listOfDto.add(new AnaliticsCategoryDto(5L, FOOD_OUT_HOME, getSumInCategory(payments, FOOD_OUT_HOME), "tooltip5"));
        listOfDto.add(new AnaliticsCategoryDto(6L, SERVICES, getSumInCategory(payments, SERVICES), "tooltip6"));
        listOfDto.add(new AnaliticsCategoryDto(7L, UNDEFINED_CATEGORY, getSumInCategory(payments, UNDEFINED_CATEGORY), "tooltip7"));

        return listOfDto;
    }

    public Long getSumInCategory(List<PaymentEntity> payments, String category) {
        return payments.stream()
                .filter(paymentEntity -> paymentEntity.getCategory().getName().equals(category))
                .mapToLong(PaymentEntity::getSum)
                .sum();
    }

    public Long getSumInLongMonth(List<PaymentEntity> payments, Month month) {
        return payments.stream()
                .filter(paymentEntity -> paymentEntity.getCreatedTime().isAfter(LocalDateTime.of(LocalDateTime.now().getYear(), month, 1, 0, 0))
                        && paymentEntity.getCreatedTime().isBefore(LocalDateTime.of(LocalDateTime.now().getYear(), month, 31, 23, 59)))
                .mapToLong(PaymentEntity::getSum)
                .sum();
    }

    private Long getSumInShortMonth(List<PaymentEntity> payments, Month month) {
        return payments.stream()
                .filter(paymentEntity -> paymentEntity.getCreatedTime().isAfter(LocalDateTime.of(LocalDateTime.now().getYear(), month, 1, 0, 0))
                        && paymentEntity.getCreatedTime().isBefore(LocalDateTime.of(LocalDateTime.now().getYear(), month, 30, 23, 59)))
                .mapToLong(PaymentEntity::getSum)
                .sum();
    }
}
