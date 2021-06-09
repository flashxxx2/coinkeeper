package payments.mapper;

import payments.dto.CategoryDto;
import payments.dto.FileUploadDto;
import payments.dto.PaymentDto;
import payments.entity.CategoryEntity;
import payments.entity.FileUploadEntity;
import payments.entity.PaymentEntity;
import payments.models.FileModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class Mapper {
    public static PaymentEntity toEntity(PaymentDto paymentDto) {
        final var paymentEntity = new PaymentEntity();
        paymentEntity.setId(paymentDto.getId());
        paymentEntity.setSum(paymentDto.getSum());
        paymentEntity.setCreatedTime(LocalDateTime.now());
        paymentEntity.setCategory(toEntityCategory(paymentDto.getCategory()));
        paymentEntity.setComment(paymentDto.getComment());
        paymentEntity.setPaymentFiles(toEntityListFileUpload(paymentDto.getFileUpload()));
        return paymentEntity;
    }

    public static FileUploadEntity toEntityFileUpload(FileUploadDto fileUploadDto) {
        final var entity = new FileUploadEntity();
        entity.setName(fileUploadDto.getFileName());
        entity.setUrl(fileUploadDto.getUrl());
        return entity;
    }

    public static FileModel convertToFileModel(FileUploadEntity entity) {
        FileModel dto = new FileModel();
        dto.setId(entity.getId());
        dto.setFileName(entity.getName());
        dto.setUrl(entity.getUrl());
        return dto;
    }

    public static List<FileUploadEntity> toEntityListFileUpload(List<FileUploadDto> fileUploadDtos) {
        return fileUploadDtos.stream()
                .map(Mapper::toEntityFileUpload)
                .collect(Collectors.toList());
    }

    public static FileUploadDto toDtoFileUpload(FileUploadEntity entity) {
        final var dto = new FileUploadDto();
        dto.setId(entity.getId());
        dto.setFileName(entity.getName());
        dto.setUrl(entity.getUrl());
        return dto;
    }

    public static List<FileUploadDto> toDtoListFileUpload(List<FileUploadEntity> entityList) {
        return entityList.stream()
                .map(Mapper::toDtoFileUpload)
                .collect(Collectors.toList());
    }

    public static CategoryEntity toEntityCategory(CategoryDto category) {

        final var categoryEntity = new CategoryEntity();
        categoryEntity.setId(category.getId());
        categoryEntity.setName(category.getName());
        return categoryEntity;
    }

    public static PaymentDto toDto(PaymentEntity paymentEntity) {
        final var paymentDto = new PaymentDto();
        paymentDto.setSum(paymentEntity.getSum());
        paymentDto.setCreatedTime(paymentEntity.getCreatedTime());
        paymentDto.setCategory(toDtoCategory(paymentEntity.getCategory()));
        paymentDto.setId(paymentEntity.getId());
        paymentDto.setComment(paymentEntity.getComment());
        paymentDto.setFileUpload(toDtoListFileUpload(paymentEntity.getPaymentFiles()));
        return paymentDto;
    }

    public static CategoryDto toDtoCategory(CategoryEntity categoryEntity) {
        final var categoryDto = new CategoryDto();
        categoryDto.setId(categoryEntity.getId());
        categoryDto.setName(categoryEntity.getName());
        return categoryDto;
    }
}
