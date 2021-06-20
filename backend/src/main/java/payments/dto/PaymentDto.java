package payments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDto {
    private Long id;
    private LocalDateTime createdTime;
    private Long sum;
    private CategoryDto category;
    private String comment;
    private List<FileUploadDto> fileUpload;
    private String info;
}
