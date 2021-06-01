package payments.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import payments.dto.MediaUploadDto;
import payments.service.MediaService;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("media")
public class MediaController {

    private final MediaService mediaService;
    private final Log log = LogFactory.getLog(getClass());


    @PostMapping("/upload/photo")
    public MediaUploadDto downloadImage(@RequestParam MultipartFile file) {
        log.debug(file.getOriginalFilename());
        return mediaService.downloadImage(file);
    }

    @RequestMapping(value = "/image/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] uploadImage(@PathVariable UUID id) {
        return mediaService.uploadImage(id);
    }
}
