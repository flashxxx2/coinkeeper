package payments.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import payments.dto.AnaliticsDto;
import payments.entity.AnaliticsEntity;
import payments.service.AnaliticsService;

@RestController
@RequestMapping("/analitics")
@RequiredArgsConstructor
public class AnaliticsController {

    private final AnaliticsService service;

    @GetMapping
    private AnaliticsDto getAnalitics(@RequestHeader("X-Profile") String userName) {
        return service.getUserAnalitics(userName);
    }

    @PostMapping("/save")
    private AnaliticsEntity saveAnalitics(@RequestBody AnaliticsDto analiticsDto) {
        return service.save(analiticsDto);
    }

    @PutMapping("/update")
    private AnaliticsEntity updateAnalitics(@RequestBody AnaliticsDto analiticsDto) {
        return service.update(analiticsDto);
    }
}
