package payments.mapper;

import payments.dto.CategoryDto;
import payments.dto.PaymentDto;
import payments.models.CategoryEntity;
import payments.models.PaymentEntity;

public class Mapper {
    public static PaymentEntity toEntity(PaymentDto paymentDto) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setSum(paymentDto.getSum());
        paymentEntity.setCreatedTime(paymentDto.getCreatedTime());
        paymentEntity.setCategoryEntity(toEntityCategory(paymentDto.getCategory()));
        paymentEntity.setId(paymentDto.getId());
        paymentEntity.setComment(paymentDto.getComment());
        return paymentEntity;
    }

    private static CategoryEntity toEntityCategory(CategoryDto category) {

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(category.getId());
        categoryEntity.setName(category.getName());
        return categoryEntity;
    }

    public static PaymentDto toDto(PaymentEntity paymentEntity) {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setSum(paymentEntity.getSum());
        paymentDto.setCreatedTime(paymentEntity.getCreatedTime());
        paymentDto.setCategory(toDtoCategory(paymentEntity.getCategoryEntity()));
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
