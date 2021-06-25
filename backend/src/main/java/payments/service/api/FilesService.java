package payments.service.api;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import payments.models.FileModel;

import javax.servlet.http.HttpServletRequest;


public interface FilesService {

    ResponseEntity<FileModel> uploadFile(MultipartFile file);

    void deleteFile(Integer id);

    ResponseEntity<Resource> downloadFile(String fileName, HttpServletRequest request);
}
