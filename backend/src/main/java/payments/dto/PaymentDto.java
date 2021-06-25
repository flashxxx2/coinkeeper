package payments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import payments.models.ErrorModelInfo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDto {
    @Min(1)
    private Long id;

    @NotNull
    private LocalDateTime createdTime;

    @NotNull
    private Long sum;

    @NotNull
    private CategoryDto category;

    private String comment;
    private List<FileUploadDto> fileUpload;

    private List<ErrorModelInfo> info;
}
