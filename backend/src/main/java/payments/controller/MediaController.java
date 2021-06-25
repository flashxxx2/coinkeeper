package payments.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import payments.models.FileModel;
import payments.service.api.FilesService;
import payments.service.api.MediaService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("media")
public class MediaController {

    private final FilesService filesService;
    private final MediaService mediaService;

    @CrossOrigin(origins = "*")
    @PostMapping("/upload/photo")
    public ResponseEntity<FileModel> downloadImage(@RequestParam("file") MultipartFile file) {
        return filesService.uploadFile(file);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public void deleteFile(@PathVariable Integer id) {
        filesService.deleteFile(id);
    }

    @GetMapping(value = "/{id}/images")
    public List<FileModel> getImages(@PathVariable Long id) {
        return mediaService.getPaymentImages(id);
    }
}
