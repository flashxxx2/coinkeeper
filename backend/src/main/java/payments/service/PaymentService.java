package payments.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import payments.dto.PaymentDto;
import payments.dto.StatusDto;
import payments.mapper.Mapper;
import payments.models.PaymentEntity;
import payments.repository.PaymentRepository;
import payments.repository.StatusRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static payments.mapper.Mapper.toEntity;
import static payments.repository.PaymentSpecification.getPaymentCriteria;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final StatusRepository statusRepository;

    public void savePayment(PaymentDto paymentDto) {
        PaymentEntity paymentEntity = toEntity(paymentDto);
        paymentRepository.save(paymentEntity);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    public List<PaymentDto> findByFilter(Long statusId, LocalDateTime after, LocalDateTime before) {
        return paymentRepository.findAll(getPaymentCriteria(statusId, after, before)).stream()
                .map(Mapper::toDto)
                .collect(Collectors.toList());
    }

    public List<StatusDto> getStatuses() {

        return statusRepository.findAll().stream()
                .map(Mapper::toDto)
                .collect(Collectors.toList());
    }
}
