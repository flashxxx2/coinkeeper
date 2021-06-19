package payments.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import payments.criteria.PaymentCriteria;
import payments.dto.CategoryDto;
import payments.dto.PaymentDto;
import payments.service.PaymentService;

import javax.ws.rs.BeanParam;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public PaymentDto savePayment(@RequestBody PaymentDto paymentDto, @RequestHeader("X-Profile") String userName) {

        return paymentService.savePayment(paymentDto, userName);
    }

    @PutMapping("/{id}")
    public PaymentDto changePayment(@RequestBody PaymentDto paymentDto) {
        return paymentService.updatePayment(paymentDto);
    }

    @DeleteMapping("/{id}")
    public void deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
    }

    @GetMapping
    public Page<PaymentDto> getAllPayments(@BeanParam PaymentCriteria criteria, @RequestHeader("X-Profile") String userName) {
        return paymentService.findPayments(criteria, userName);
    }

    @GetMapping("/{id}")
    public PaymentDto getPayment(@PathVariable("id") Long id) {
        return paymentService.getPaymentById(id);
    }

    @GetMapping("/category")
    public List<CategoryDto> getPaymentCategory() {
        return paymentService.getCategory();
    }
}
