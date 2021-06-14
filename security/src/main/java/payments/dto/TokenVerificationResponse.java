package payments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TokenVerificationResponse {
  private boolean ok;
  private Object payload; // profile

  public TokenVerificationResponse(boolean ok, Object payload) {
    this.ok = ok;
    this.payload = payload;
  }
}
