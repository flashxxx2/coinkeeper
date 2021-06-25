package payments.service.impl;

import org.apache.tika.Tika;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import payments.exception.MediaUploadException;
import payments.exception.UnsupportedMediaTypeException;
import payments.models.FileModel;
import payments.service.api.FilesService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;


@Service
public class FilesServiceImpl implements FilesService {

    private final FileStorageServiceImpl fileStorageService;
    private final Tika tika;

    private final Map<String, String> supportedMediaTypeExtensions = Map.of(
            MediaType.IMAGE_PNG_VALUE, ".png",
            MediaType.IMAGE_JPEG_VALUE, ".jpg"
    );

    public FilesServiceImpl(FileStorageServiceImpl fileStorageController) {
        this.fileStorageService = fileStorageController;
        this.tika = new Tika();

    }

    @Override
    public ResponseEntity<FileModel> uploadFile(MultipartFile file) {
        try {
            final var type = tika.detect(file.getInputStream());
            Optional.ofNullable(
                    supportedMediaTypeExtensions.get(type)
            ).orElseThrow(UnsupportedMediaTypeException::new);

            FileModel model = fileStorageService.storeFile(file);
            return new ResponseEntity<>(new FileModel(model.getId(), model.getFileName(), "media/files/" + model.getUrl()), HttpStatus.CREATED);
        } catch (IOException e) {
            throw new MediaUploadException(e);
        }
    }

    @Override
    public void deleteFile(Integer id) {
        fileStorageService.delete(id);
    }

    @Override
    public ResponseEntity<Resource> downloadFile(String fileName, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFile(fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ignored) {
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
