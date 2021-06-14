package payments.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import payments.dto.ProfileDto;
import payments.dto.TokenVerificationResponse;
import payments.repository.UserRepository;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class AuthController {
  private final ObjectMapper mapper;
  private final UserRepository userRepository;

  @RequestMapping("/api/auth/verify")
  public ResponseEntity<String> verify(@RequestHeader("X-USER-PROFILE") String userName) throws JsonProcessingException {

    final var user = userRepository.findByUsername(userName);
    if (user != null) {
      final var header = mapper.writeValueAsString(
              new TokenVerificationResponse(true, new ProfileDto(
                      user.getId(), user.getUsername(), user.getPassword()
              )));
      return ResponseEntity.ok()
              .header("X-Profile", header)
              .body("ok");
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body("Неверный логин/пароль");
  }
}
