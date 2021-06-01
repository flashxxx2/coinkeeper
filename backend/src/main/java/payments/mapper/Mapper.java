package payments.mapper;

import payments.dto.CategoryDto;
import payments.dto.PaymentDto;
import payments.entity.CategoryEntity;
import payments.entity.PaymentEntity;

import java.time.LocalDateTime;

public class Mapper {
    public static PaymentEntity toEntity(PaymentDto paymentDto) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setSum(paymentDto.getSum());
        paymentEntity.setCreatedTime(LocalDateTime.now());
        paymentEntity.setCategory(toEntityCategory(paymentDto.getCategory()));
        paymentEntity.setComment(paymentDto.getComment());
        return paymentEntity;
    }

    public static CategoryEntity toEntityCategory(CategoryDto category) {

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(category.getId());
        categoryEntity.setName(category.getName());
        return categoryEntity;
    }

    public static PaymentDto toDto(PaymentEntity paymentEntity) {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setSum(paymentEntity.getSum());
        paymentDto.setCreatedTime(paymentEntity.getCreatedTime());
        paymentDto.setCategory(toDtoCategory(paymentEntity.getCategory()));
        paymentDto.setId(paymentEntity.getId());
        paymentDto.setComment(paymentEntity.getComment());
        return paymentDto;
    }

    public static CategoryDto toDtoCategory(CategoryEntity categoryEntity) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(categoryEntity.getId());
        categoryDto.setName(categoryEntity.getName());
        return categoryDto;
    }
}
