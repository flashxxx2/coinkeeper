package payments.service.impl;


import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
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
import payments.exception.IllegalCoastPurchaseException;
import payments.mapper.Mapper;
import payments.models.ErrorModelInfo;
import payments.repository.*;
import payments.service.api.PaymentService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static payments.mapper.Mapper.*;

@Service
@AllArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final CategoryRepository categoryRepository;
    private final MediaRepository mediaRepository;

    private final MessageSource messageSource;
    private final AnaliticsRepository analiticsRepository;


    private static final String STATIC = "https://cs6.pikabu.ru/post_img/2015/07/04/10/1436029898_1190099444.jpg";
    private static final String DEFAULT = "Нет фото";


    @Override
    public PaymentDto savePayment(PaymentDto paymentDto, String userName) {
        var paymentEntity = toEntity(paymentDto, userName);

        try {
            if (paymentDto.getSum() > analiticsRepository.getBalance(userName)) {
                throw new IllegalCoastPurchaseException();
            }

            final var analitics = analiticsRepository.getAnaliticsEntitiesByUserName(userName);
            analitics.ifPresent(entity -> entity.setBalance(analiticsRepository.getBalance(userName) - paymentDto.getSum()));

            analitics.ifPresent(analiticsRepository::save);

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
        } catch (IllegalCoastPurchaseException e) {
            paymentDto.setInfo(getInfo(paymentEntity, "so.big.purchase"));
        }
        return paymentDto;
    }

    @Override
    public void deletePayment(Long id, String userName) {
        final var analitics = analiticsRepository.getAnaliticsEntitiesByUserName(userName);
        analitics.ifPresent(entity -> entity.setBalance(analiticsRepository.getBalance(userName) + paymentRepository.getById(id)
                .getSum()));
        analitics.ifPresent(analiticsRepository::save);

        paymentRepository.deleteById(id);
    }

    @Override
    public Page<PaymentDto> findPayments(PaymentCriteria criteria, String userName) {
        criteria.setUserName(userName);
        return paymentRepository
                .findAll(new PaymentSpecification(criteria), PageRequest.of(criteria.getPageNumber(), criteria.getPageSize(), criteria.getSort()))
                .map(Mapper::toDto);
    }

    @Override
    public List<CategoryDto> getCategory() {
        return categoryRepository.findAll()
                .stream()
                .map(Mapper::toDtoCategory)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentDto updatePayment(PaymentDto paymentDto, String userName) {
        var entity = paymentRepository.getById(paymentDto.getId());
        var analitics = analiticsRepository.getAnaliticsEntitiesByUserName(userName);

        final var difference = entity.getSum() - paymentDto.getSum();

        if (analitics.isPresent()) {
            final var balance = analitics.get().getBalance() + difference;
            analitics.get().setBalance(balance);
        }

        analitics.ifPresent(analiticsRepository::save);

        entity.setCreatedTime(paymentDto.getCreatedTime());
        entity.setSum(paymentDto.getSum());
        entity.setComment(paymentDto.getComment());
        entity.setCategory(toEntityCategory(paymentDto.getCategory()));
        if (!paymentDto.getFileUpload().isEmpty()) {
            mediaRepository.saveAll(toEntityListFileUpload(paymentDto.getFileUpload()));
        } else
            mediaRepository.deleteByPaymentId(entity.getId());
        return toDto(paymentRepository.save(entity));
    }

    @Override
    @Cacheable(value = "operations")
    public PaymentDto getPaymentById(Long id) {
        return toDto(paymentRepository.getOne(id));
    }

    private static List<FileUploadDto> getStaticFileEntity() {
        final var dto = new FileUploadDto();
        dto.setUrl(STATIC);
        dto.setFileName(DEFAULT);
        return Collections.singletonList(dto);
    }

    private List<ErrorModelInfo> getInfo(PaymentEntity paymentEntity, String errorCode) {
        List<ErrorModelInfo> info = new ArrayList<>();
        info.add(new ErrorModelInfo(messageSource.getMessage(errorCode, new Object[]{paymentEntity.getSum()}, Locale.getDefault()), null));
        return info;
    }
}
