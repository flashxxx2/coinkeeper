package payments.mapper;

import payments.dto.PaymentDto;
import payments.dto.StatusDto;
import payments.models.PaymentEntity;
import payments.models.StatusEntity;

public class Mapper {
    public static PaymentEntity toEntity(PaymentDto paymentDto) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setSum(paymentDto.getSum());
        paymentEntity.setStatusEntity(toEntity(paymentDto.getStatus()));
        paymentEntity.setCreatedTime(paymentDto.getCreatedTime());
        paymentEntity.setCommission(paymentDto.getCommission());
        paymentEntity.setId(paymentDto.getId());
        return paymentEntity;
    }

    private static StatusEntity toEntity(StatusDto status) {

        StatusEntity statusEntity = new StatusEntity();
        statusEntity.setId(status.getId());
        statusEntity.setName(status.getName());
        return statusEntity;
    }

    public static PaymentDto toDto(PaymentEntity paymentEntity) {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setSum(paymentEntity.getSum());
        paymentDto.setStatus(toDto(paymentEntity.getStatusEntity()));
        paymentDto.setCreatedTime(paymentEntity.getCreatedTime());
        paymentDto.setCommission(paymentEntity.getCommission());
        paymentDto.setId(paymentEntity.getId());
        return paymentDto;
    }

    public static StatusDto toDto(StatusEntity statusEntity) {
        StatusDto statusDto = new StatusDto();
        statusDto.setId(statusEntity.getId());
        statusDto.setName(statusEntity.getName());
        return statusDto;
    }
}
