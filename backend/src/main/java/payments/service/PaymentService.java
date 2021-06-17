package payments.service;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import payments.criteria.PaymentCriteria;
import payments.dto.CategoryDto;
import payments.dto.PaymentDto;
import payments.entity.FileUploadEntity;
import payments.mapper.Mapper;
import payments.repository.CategoryRepository;
import payments.repository.MediaRepository;
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
    private final MediaRepository mediaRepository;

    @Transactional
    public PaymentDto savePayment(PaymentDto paymentDto) {
        var paymentEntity = toEntity(paymentDto);
        paymentRepository.save(paymentEntity);
        if (!paymentDto.getFileUpload().isEmpty()) {
            List<FileUploadEntity> files = toEntityListFileUpload(paymentDto.getFileUpload()).stream()
                    .peek(file -> file.setPayment(paymentEntity))
                    .collect(Collectors.toList());
            mediaRepository.saveAll(files);
        }
        return paymentDto;
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

    @Transactional
    public PaymentDto updatePayment(PaymentDto paymentDto) {
        var entity = paymentRepository.getById(paymentDto.getId());
        entity.setCreatedTime(LocalDateTime.now());
        entity.setSum(paymentDto.getSum());
        entity.setComment(paymentDto.getComment());
        entity.setCategory(toEntityCategory(paymentDto.getCategory()));
        if(!paymentDto.getFileUpload().isEmpty()) {
            mediaRepository.saveAll(toEntityListFileUpload(paymentDto.getFileUpload()));
        } else
            mediaRepository.deleteByPaymentId(entity.getId());
        return toDto(paymentRepository.save(entity));
    }

    public PaymentDto getPaymentById(Long id) {
        return toDto(paymentRepository.getOne(id));
    }
}
