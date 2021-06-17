package payments.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import payments.dto.AnaliticsDto;
import payments.entity.AnaliticsEntity;
import payments.entity.UserEntity;
import payments.service.AnaliticsService;

import java.security.Principal;

@RestController
@RequestMapping("/analitics")
@RequiredArgsConstructor
public class AnaliticsController {

    private final AnaliticsService service;

    @GetMapping
    private AnaliticsDto getAnalitics(Principal authentication) {
        long id = ((UserEntity) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal()).getId();
        return service.getUserAnalitics(id);
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
