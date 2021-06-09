package payments.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import payments.criteria.FileCriteria;
import payments.models.FileModel;
import payments.service.FilesService;
import payments.service.MediaService;

import javax.ws.rs.BeanParam;

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

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    public Page<FileModel> getImages(@PathVariable Long id, @BeanParam FileCriteria criteria) {
        return mediaService.getPaymentImages(id, criteria);
    }
}
