package payments.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import payments.dto.AnaliticsCategoryDto;
import payments.dto.AnaliticsConsumptionDto;
import payments.dto.AnaliticsDto;
import payments.entity.AnaliticsEntity;
import payments.entity.PaymentEntity;
import payments.mapper.Mapper;
import payments.repository.AnaliticsRepository;
import payments.repository.CategoryRepository;
import payments.repository.PaymentRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<AnaliticsConsumptionDto> getConsumptionGraph(String userName) {
        final var payments = paymentRepository.findAllPaymentsInCurrentYear(userName);
        var sumInJanuary = payments.stream()
                .filter(paymentEntity -> paymentEntity.getCreatedTime().isAfter(LocalDateTime.of(LocalDateTime.now().getYear(), Month.JANUARY, 1, 0, 0 ))
                && paymentEntity.getCreatedTime().isBefore(LocalDateTime.of(LocalDateTime.now().getYear(), Month.JANUARY, 31, 23, 59 )))
                .mapToLong(PaymentEntity::getSum)
                .sum();

        var sumInFebruary = payments.stream()
                .filter(paymentEntity -> paymentEntity.getCreatedTime().isAfter(LocalDateTime.of(LocalDateTime.now().getYear(), Month.FEBRUARY, 1, 0, 0 ))
                && paymentEntity.getCreatedTime().isBefore(LocalDateTime.of(LocalDateTime.now().getYear(), Month.FEBRUARY, 28, 23, 59 )))
                .mapToLong(PaymentEntity::getSum)
                .sum();

        var sumInMarch = payments.stream()
                .filter(paymentEntity -> paymentEntity.getCreatedTime().isAfter(LocalDateTime.of(LocalDateTime.now().getYear(), Month.MARCH, 1, 0, 0 ))
                && paymentEntity.getCreatedTime().isBefore(LocalDateTime.of(LocalDateTime.now().getYear(), Month.MARCH, 31, 23, 59 )))
                .mapToLong(PaymentEntity::getSum)
                .sum();

        var sumInApril = payments.stream()
                .filter(paymentEntity -> paymentEntity.getCreatedTime().isAfter(LocalDateTime.of(LocalDateTime.now().getYear(), Month.APRIL, 1, 0, 0 ))
                && paymentEntity.getCreatedTime().isBefore(LocalDateTime.of(LocalDateTime.now().getYear(), Month.APRIL, 30, 23, 59 )))
                .mapToLong(PaymentEntity::getSum)
                .sum();

        var sumInMay = payments.stream()
                .filter(paymentEntity -> paymentEntity.getCreatedTime().isAfter(LocalDateTime.of(LocalDateTime.now().getYear(), Month.MAY, 1, 0, 0 ))
                && paymentEntity.getCreatedTime().isBefore(LocalDateTime.of(LocalDateTime.now().getYear(), Month.MAY, 31, 23, 59 )))
                .mapToLong(PaymentEntity::getSum)
                .sum();

        var sumInJune = payments.stream()
                .filter(paymentEntity -> paymentEntity.getCreatedTime().isAfter(LocalDateTime.of(LocalDateTime.now().getYear(), Month.JUNE, 1, 0, 0 ))
                && paymentEntity.getCreatedTime().isBefore(LocalDateTime.of(LocalDateTime.now().getYear(), Month.JUNE, 30, 23, 59 )))
                .mapToLong(PaymentEntity::getSum)
                .sum();

        var sumInJuly = payments.stream()
                .filter(paymentEntity -> paymentEntity.getCreatedTime().isAfter(LocalDateTime.of(LocalDateTime.now().getYear(), Month.JULY, 1, 0, 0 ))
                && paymentEntity.getCreatedTime().isBefore(LocalDateTime.of(LocalDateTime.now().getYear(), Month.JULY, 31, 23, 59 )))
                .mapToLong(PaymentEntity::getSum)
                .sum();

        var sumInAugust = payments.stream()
                .filter(paymentEntity -> paymentEntity.getCreatedTime().isAfter(LocalDateTime.of(LocalDateTime.now().getYear(), Month.AUGUST, 1, 0, 0 ))
                && paymentEntity.getCreatedTime().isBefore(LocalDateTime.of(LocalDateTime.now().getYear(), Month.AUGUST, 30, 23, 59 )))
                .mapToLong(PaymentEntity::getSum)
                .sum();

        var sumInSeptember = payments.stream()
                .filter(paymentEntity -> paymentEntity.getCreatedTime().isAfter(LocalDateTime.of(LocalDateTime.now().getYear(), Month.SEPTEMBER, 1, 0, 0 ))
                && paymentEntity.getCreatedTime().isBefore(LocalDateTime.of(LocalDateTime.now().getYear(), Month.SEPTEMBER, 30, 23, 59 )))
                .mapToLong(PaymentEntity::getSum)
                .sum();

        var sumInOctober = payments.stream()
                .filter(paymentEntity -> paymentEntity.getCreatedTime().isAfter(LocalDateTime.of(LocalDateTime.now().getYear(), Month.OCTOBER, 1, 0, 0 ))
                && paymentEntity.getCreatedTime().isBefore(LocalDateTime.of(LocalDateTime.now().getYear(), Month.OCTOBER, 30, 23, 59 )))
                .mapToLong(PaymentEntity::getSum)
                .sum();

        var sumInNovember = payments.stream()
                .filter(paymentEntity -> paymentEntity.getCreatedTime().isAfter(LocalDateTime.of(LocalDateTime.now().getYear(), Month.NOVEMBER, 1, 0, 0 ))
                && paymentEntity.getCreatedTime().isBefore(LocalDateTime.of(LocalDateTime.now().getYear(), Month.NOVEMBER, 30, 23, 59 )))
                .mapToLong(PaymentEntity::getSum)
                .sum();

        var sumInDecember = payments.stream()
                .filter(paymentEntity -> paymentEntity.getCreatedTime().isAfter(LocalDateTime.of(LocalDateTime.now().getYear(), Month.DECEMBER, 1, 0, 0 ))
                && paymentEntity.getCreatedTime().isBefore(LocalDateTime.of(LocalDateTime.now().getYear(), Month.DECEMBER, 30, 23, 59 )))
                .mapToLong(PaymentEntity::getSum)
                .sum();


        List<AnaliticsConsumptionDto> listOfDto = new ArrayList<>();
        listOfDto.add(new AnaliticsConsumptionDto(1L, "January", sumInJanuary));
        listOfDto.add(new AnaliticsConsumptionDto(1L, "February", sumInFebruary));
        listOfDto.add(new AnaliticsConsumptionDto(1L, "March", sumInMarch));
        listOfDto.add(new AnaliticsConsumptionDto(1L, "April", sumInApril));
        listOfDto.add(new AnaliticsConsumptionDto(1L, "May", sumInMay));
        listOfDto.add(new AnaliticsConsumptionDto(1L, "June", sumInJune));
        listOfDto.add(new AnaliticsConsumptionDto(1L, "July", sumInJuly));
        listOfDto.add(new AnaliticsConsumptionDto(1L, "August", sumInAugust));
        listOfDto.add(new AnaliticsConsumptionDto(1L, "September", sumInSeptember));
        listOfDto.add(new AnaliticsConsumptionDto(1L, "October", sumInOctober));
        listOfDto.add(new AnaliticsConsumptionDto(1L, "November", sumInNovember));
        listOfDto.add(new AnaliticsConsumptionDto(1L, "December", sumInDecember));

        return listOfDto;
    }

    public List<AnaliticsCategoryDto> getCategoryGraph(String userName) {
        final var payments = paymentRepository.findAllPaymentsInCurrentYear(userName);

        var sumInFirstCategory = payments.stream()
                .filter(paymentEntity -> paymentEntity.getCategory().getName().equals("Продукты"))
                .mapToLong(PaymentEntity::getSum)
                .sum();

        var sumInSecondCategory = payments.stream()
                .filter(paymentEntity -> paymentEntity.getCategory().getName().equals("Транспорт"))
                .mapToLong(PaymentEntity::getSum)
                .sum();

        var sumInThirdCategory = payments.stream()
                .filter(paymentEntity -> paymentEntity.getCategory().getName().equals("Развлечения"))
                .mapToLong(PaymentEntity::getSum)
                .sum();

        var sumInFourthCategory = payments.stream()
                .filter(paymentEntity -> paymentEntity.getCategory().getName().equals("Романтика"))
                .mapToLong(PaymentEntity::getSum)
                .sum();

        var sumInFifthCategory = payments.stream()
                .filter(paymentEntity -> paymentEntity.getCategory().getName().equals("Еда вне дома"))
                .mapToLong(PaymentEntity::getSum)
                .sum();

        var sumInSixthCategory = payments.stream()
                .filter(paymentEntity -> paymentEntity.getCategory().getName().equals("Услуги"))
                .mapToLong(PaymentEntity::getSum)
                .sum();

        var sumInSeventhCategory = payments.stream()
                .filter(paymentEntity -> paymentEntity.getCategory().getName().equals("Неопределенная категория"))
                .mapToLong(PaymentEntity::getSum)
                .sum();

        List<AnaliticsCategoryDto> listOfDto = new ArrayList<>();
        listOfDto.add(new AnaliticsCategoryDto(1L, "Продукты", sumInFirstCategory, "tooltip1"));
        listOfDto.add(new AnaliticsCategoryDto(2L, "Транспорт", sumInSecondCategory, "tooltip2"));
        listOfDto.add(new AnaliticsCategoryDto(3L, "Развлечения", sumInThirdCategory, "tooltip3"));
        listOfDto.add(new AnaliticsCategoryDto(4L, "Романтика", sumInFourthCategory, "tooltip4"));
        listOfDto.add(new AnaliticsCategoryDto(5L, "Еда вне дома", sumInFifthCategory, "tooltip5"));
        listOfDto.add(new AnaliticsCategoryDto(6L, "Услуги", sumInSixthCategory, "tooltip6"));
        listOfDto.add(new AnaliticsCategoryDto(7L, "Неопределенная категория", sumInSeventhCategory, "tooltip7"));

        return listOfDto;
    }
}
