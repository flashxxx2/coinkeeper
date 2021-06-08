package payments.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping
public class ResourceController {
  @GetMapping("/user/me")
  public Principal principal(Principal principal) {
    System.out.println(principal);
    return principal;
  }

  @PreAuthorize("#oauth2.clientHasRole('ROLE_CLIENT')")
  @GetMapping("/oauth")
  public String oauth() {
    return "oauth";
  }
}
