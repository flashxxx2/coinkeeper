package payments.service;


import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import payments.criteria.PaymentCriteria;
import payments.dto.CategoryDto;
import payments.dto.FileUploadDto;
import payments.dto.PaymentDto;
import payments.entity.FileUploadEntity;
import payments.entity.PaymentEntity;
import payments.mapper.Mapper;
import payments.repository.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static payments.mapper.Mapper.*;

@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final CategoryRepository categoryRepository;
    private final MediaRepository mediaRepository;

    private final MessageSource messageSource;
    private final AnaliticsRepository analiticsRepository;


    private static final String STATIC = "https://cs6.pikabu.ru/post_img/2015/07/04/10/1436029898_1190099444.jpg";


    @Transactional
    public PaymentDto savePayment(PaymentDto paymentDto, String userName) {
        var paymentEntity = toEntity(paymentDto, userName);
//        try {
//            analiticsRepository.getUserPlannedConsumption()
//            if(paymentDto.getSum() >)
//        }

        final var analitics = analiticsRepository.getAnaliticsEntitiesByUserName(userName);
        analitics.ifPresent(entity -> entity.setBalance(analiticsRepository.getBalance(userName) - paymentDto.getSum()));
        analiticsRepository.save(analitics.get());

        paymentRepository.save(paymentEntity);
        List<FileUploadEntity> files;
        if (paymentDto.getFileUpload() != null) {
            files = toEntityListFileUpload(paymentDto.getFileUpload()).stream()
                    .peek(file -> file.setPayment(paymentEntity))
                    .collect(Collectors.toList());
        } else {
            files = toEntityListFileUpload(getStaticFileEntity()).stream()
                    .peek(file -> file.setPayment(paymentEntity))
                    .collect(Collectors.toList());
        }
        mediaRepository.saveAll(files);
        return paymentDto;
    }

    public void deletePayment(Long id, String userName) {
        final var analitics = analiticsRepository.getAnaliticsEntitiesByUserName(userName);
        analitics.ifPresent(entity -> entity.setBalance(analiticsRepository.getBalance(userName) + paymentRepository.getById(id).getSum()));
        analiticsRepository.save(analitics.get());

        paymentRepository.deleteById(id);
    }

    @Transactional
    public Page<PaymentDto> findPayments(PaymentCriteria criteria, String userName) {
        criteria.setUserName(userName);
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

    public static List<FileUploadDto> getStaticFileEntity() {
        final var dto = new FileUploadDto();
        dto.setUrl(STATIC);
        dto.setFileName("default");
        return Collections.singletonList(dto);
    }

    private String getInfo(PaymentEntity paymentEntity, String errorCode) {
        return messageSource.getMessage(errorCode, new Object[]{paymentEntity.getSum()}, Locale.getDefault());
    }

}
