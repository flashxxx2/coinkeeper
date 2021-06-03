package payments.dto;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class FileUploadDto {

    private Long id;
    private String url;
    private String fileName;
}
