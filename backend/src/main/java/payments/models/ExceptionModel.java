package payments.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionModel {
    private String message;
    private String exceptionName;

}