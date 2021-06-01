package payments.service;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import payments.criteria.PaymentCriteria;
import payments.dto.CategoryDto;
import payments.dto.PaymentDto;
import payments.mapper.Mapper;
import payments.entity.PaymentEntity;
import payments.repository.CategoryRepository;
import payments.repository.PaymentRepository;
import payments.repository.PaymentSpecification;

import java.time.LocalDateTime;
import java.util.List;
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
    public Page<PaymentDto> findPayments(PaymentCriteria criteria) {
        return paymentRepository
                .findAll(new PaymentSpecification(criteria), PageRequest.of(criteria.getPageNumber(), criteria.getPageSize(), criteria.getSort()))
                .map(Mapper::toDto);
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
