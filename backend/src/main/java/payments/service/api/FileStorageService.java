package payments.service.api;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import payments.models.FileModel;

public interface FileStorageService {

    void delete(Integer id);

    FileModel storeFile(MultipartFile file);

    Resource loadFile(String fileName);
}
