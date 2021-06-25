package payments.service.api;


import org.springframework.data.domain.Page;
import payments.criteria.PaymentCriteria;
import payments.dto.CategoryDto;
import payments.dto.PaymentDto;

import java.util.List;

public interface PaymentService {

    PaymentDto savePayment(PaymentDto paymentDto, String userName);

    void deletePayment(Long id, String userName);

    Page<PaymentDto> findPayments(PaymentCriteria criteria, String userName);

    List<CategoryDto> getCategory();

    PaymentDto updatePayment(PaymentDto paymentDto, String userName);

    PaymentDto getPaymentById(Long id);
}
