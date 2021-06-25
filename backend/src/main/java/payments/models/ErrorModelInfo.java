package payments.models;


import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ErrorModelInfo {
    private String name;
    private String href;
}
