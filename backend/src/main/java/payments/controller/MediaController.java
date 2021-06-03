package payments.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import payments.models.FileModel;
import payments.service.FilesService;

import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
@RequestMapping("media")
public class MediaController {

    private final FilesService filesService;

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

    @CrossOrigin(origins = "*")
    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String fileName, HttpServletRequest request) {
        return filesService.downloadFile(fileName, request);
    }
}
