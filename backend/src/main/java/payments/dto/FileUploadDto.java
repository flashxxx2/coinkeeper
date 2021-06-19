package payments.dto;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class FileUploadDto {
    public FileUploadDto(String url) {
        this.url = url;
    }

    private Long id;
    private String url;
    private String fileName;
}
