package payments.models;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileModel {
    private Long id;
    private String fileName;
    private String url;

}
