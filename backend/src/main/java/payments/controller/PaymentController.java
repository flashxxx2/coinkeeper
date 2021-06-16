package payments.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import payments.criteria.PaymentCriteria;
import payments.dto.CategoryDto;
import payments.dto.PaymentDto;
import payments.service.PaymentService;

import javax.ws.rs.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public PaymentDto savePayment(@RequestBody PaymentDto paymentDto) {
        return paymentService.savePayment(paymentDto);
    }

    @PutMapping("/{id}")
    public PaymentDto changePayment(@RequestBody PaymentDto paymentDto) {
        return paymentService.updatePayment(paymentDto);
    }

    @DeleteMapping("/{id}")
    public void deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
    }

    @CrossOrigin(origins = "*")
    @GetMapping
    public Page<PaymentDto> getAllPayments(@BeanParam PaymentCriteria criteria) {
        return paymentService.findPayments(criteria);
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
