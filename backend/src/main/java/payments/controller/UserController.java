package payments.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import payments.dto.UserDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class UserController {

    @GetMapping("/user")
    public UserDto getUSer(@RequestHeader("X-Profile") String userName) {
        final var user = new UserDto();
            user.setUserName(userName);
        return user;
    }
}
