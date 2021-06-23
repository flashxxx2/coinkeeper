package payments.dto;

import lombok.*;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class FileUploadDto {
    @Min(1)
    private Long id;
    private String url;
    private String fileName;
}
