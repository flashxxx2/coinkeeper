package payments.models;


import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ErrorModelInfo {
    private String name;
    private String href;
}
