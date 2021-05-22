package payments.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import payments.dto.CategoryDto;
import payments.dto.PaymentDto;
import payments.mapper.Mapper;
import payments.models.PaymentEntity;
import payments.repository.CategoryRepository;
import payments.repository.PaymentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static payments.mapper.Mapper.toEntity;
import static payments.repository.PaymentSpecification.getPaymentCriteria;

@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final CategoryRepository categoryRepository;

    public void savePayment(PaymentDto paymentDto) {
        PaymentEntity paymentEntity = toEntity(paymentDto);
        paymentRepository.save(paymentEntity);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    public List<PaymentDto> findByFilter(Long categoryId, LocalDateTime after, LocalDateTime before) {
        return paymentRepository.findAll(getPaymentCriteria(categoryId, after, before))
                .stream()
                .map(Mapper::toDto)
                .collect(Collectors.toList());
    }

    public List<CategoryDto> getCategory() {
        return categoryRepository.findAll()
                .stream()
                .map(Mapper::toDtoCategory)
                .collect(Collectors.toList());
    }
}
