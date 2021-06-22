package payments.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import payments.dto.AnaliticsCategoryDto;
import payments.dto.AnaliticsConsumptionDto;
import payments.dto.AnaliticsDto;
import payments.entity.AnaliticsEntity;
import payments.service.AnaliticsService;

import java.util.List;

@RestController
@RequestMapping("/analitics")
@RequiredArgsConstructor
public class AnaliticsController {

    private final AnaliticsService service;

    @GetMapping
    private AnaliticsDto getAnalitics(@RequestHeader("X-Profile") String userName) {
        return service.getUserAnalitics(userName);
    }

    @GetMapping("/getCategoryGraph")
    private List<AnaliticsCategoryDto> getCategoryGraph(@RequestHeader("X-Profile") String userName) {
        return service.getCategoryGraph(userName);
    }

    @GetMapping("/getConsumptionGraph")
    private List<AnaliticsConsumptionDto> getConsumptionGraph(@RequestHeader("X-Profile") String userName) {
        return service.getConsumptionGraph(userName);
    }

    @PostMapping("/save")
    private AnaliticsEntity saveAnalitics(@RequestBody AnaliticsDto analiticsDto) {
        return service.save(analiticsDto);
    }

    @PutMapping("/update")
    private AnaliticsEntity updateAnalitics(@RequestBody AnaliticsDto analiticsDto, @RequestHeader("X-Profile") String userName) {
        return service.update(analiticsDto, userName);
    }
}
