package payments.dto;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@RedisHash("files")
public class FileUploadDto {
    @Min(1)
    private Long id;
    private String url;
    private String fileName;
}
