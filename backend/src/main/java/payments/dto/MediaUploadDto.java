package payments.dto;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class MediaUploadDto {

    private UUID id;
    private String webPath;
    private String name;
}
