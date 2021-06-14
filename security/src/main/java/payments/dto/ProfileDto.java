package payments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProfileDto {
  private long id;
  private String login;
  private String password;

  public ProfileDto(long id, String login, String password) {
    this.id = id;
    this.login = login;
    this.password = password;
  }
}
