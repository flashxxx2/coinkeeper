package payments.models;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class FileModel {

    private UUID id;
    private String path;
    private String name;
}