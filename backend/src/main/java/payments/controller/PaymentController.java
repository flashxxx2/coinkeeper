package payments.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import payments.dto.CategoryDto;
import payments.dto.PaymentDto;
import payments.service.PaymentService;

import java.time.LocalDateTime;
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

    @GetMapping
    public List<PaymentDto> findAll(@RequestParam(required = false) Long categoryId,
                                    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime after,
                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                    @RequestParam(required = false) LocalDateTime before) {
        return paymentService.findByFilter(categoryId, after, before);
    }

    @GetMapping("/{id}")
    public PaymentDto getById(@PathVariable("id") Long id) {
        return paymentService.getPaymentById(id);
    }

    @GetMapping("/category")
    public List<CategoryDto> getCategory() {
        return paymentService.getCategory();
    }
}
