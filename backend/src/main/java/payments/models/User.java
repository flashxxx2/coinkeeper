package payments.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class User {
  private long id;
  private String name;
  private String username;
  private String password;
  boolean accountNonExpired;
  boolean accountNonLocked;
  boolean credentialsNonExpired;
  boolean enabled;
}
