package payments.service;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import payments.criteria.PaymentCriteria;
import payments.dto.CategoryDto;
import payments.dto.PaymentDto;
import payments.mapper.Mapper;
import payments.models.PaymentEntity;
import payments.repository.CategoryRepository;
import payments.repository.PaymentRepository;
import payments.repository.PaymentSpecification;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static payments.mapper.Mapper.*;

@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public PaymentDto savePayment(PaymentDto paymentDto) {
        PaymentEntity paymentEntity = toEntity(paymentDto);
        return toDto(paymentRepository.save(paymentEntity));
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    @Transactional
    public Page<PaymentDto> findByFilter(PaymentCriteria criteria) {
        PageRequest page = PageRequest.of(criteria.getPageNumber(), criteria.getPageSize(), criteria.getSort());

        PaymentSpecification spec = new PaymentSpecification(criteria);

        final AtomicInteger counter = new AtomicInteger();

        List<PaymentDto> list = paymentRepository
                .findAll(spec, criteria.getSort())
                .stream()
                .map(Mapper::toDto)
                .collect(Collectors.toList());

        Optional<Sort.Order> categoryOrder = criteria.getSort().stream()
                .filter(order -> order.getProperty().equals("categoryId"))
                .findAny();

        int totalElements = list.size() - counter.get();

        //Сортируем по наименованию категории
        return categoryOrder.map(order -> sortApplicationPageByCategory(
                order,
                list,
                page,
                totalElements
        )).orElse(new PageImpl<>(list, page, totalElements));
    }

    private Page<PaymentDto> sortApplicationPageByCategory(
            Sort.Order order,
            List<PaymentDto> list,
            Pageable pageable,
            long size
    ) {
        List<PaymentDto> resultList;
        if (order.isAscending()) {
            resultList = list.stream()
                    .sorted(Comparator.comparing(o -> o.getCategory().getName()))
                    .collect(Collectors.toList());
        } else {
            resultList = list.stream()
                    .sorted((o1, o2) -> o2
                            .getCategory().getName()
                            .compareTo(o1.getCategory().getName())
                    )
                    .collect(Collectors.toList());
        }
        return new PageImpl<>(resultList, pageable, size);
    }

    public List<CategoryDto> getCategory() {
        return categoryRepository.findAll()
                .stream()
                .map(Mapper::toDtoCategory)
                .collect(Collectors.toList());
    }

    public PaymentDto updatePayment(PaymentDto paymentDto) {
        PaymentEntity entity = new PaymentEntity();
        entity.setId(paymentDto.getId());
        entity.setCreatedTime(LocalDateTime.now());
        entity.setSum(paymentDto.getSum());
        entity.setComment(paymentDto.getComment());
        entity.setCategory(toEntityCategory(paymentDto.getCategory()));

        return toDto(paymentRepository.save(entity));
    }

    public PaymentDto getPaymentById(Long id) {
        return toDto(paymentRepository.getOne(id));
    }
}
