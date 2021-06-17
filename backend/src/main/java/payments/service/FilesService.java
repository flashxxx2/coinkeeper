package payments.service;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import payments.models.FileModel;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FilesService {

    private final FileStorageService fileStorageService;

    public FilesService(FileStorageService fileStorageController) {
        this.fileStorageService = fileStorageController;
    }

    public ResponseEntity<FileModel> uploadFile(MultipartFile file) {
        FileModel model = fileStorageService.storeFile(file);
        return new ResponseEntity<>(new FileModel(model.getId(), model.getFileName(), "media/files/"+model.getUrl()), HttpStatus.CREATED);
    }

    public ResponseEntity<List<FileModel>> uploadMultipleFiles(MultipartFile[] files) {
        return new ResponseEntity<>(
                Arrays.stream(files)
                        .map(file -> uploadFile(file).getBody())
                        .collect(Collectors.toList()), HttpStatus.CREATED
        );
    }

    public void deleteFile(Integer id) {
        fileStorageService.delete(id);
    }

    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFile(fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ignored) {}

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
