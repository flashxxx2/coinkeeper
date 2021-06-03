package payments.models;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FileModel {
    private final Integer id;
    private final String fileName;
    private final String url;
    private boolean stored;

    public FileModel(Integer id, String fileName, String url) {
        this.id = id;
        this.fileName = fileName;
        this.url = url;
        stored = false;
    }
}
