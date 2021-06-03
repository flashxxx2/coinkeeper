package payments.service;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import payments.repository.MediaRepository;
import payments.repository.PaymentRepository;

import java.util.Map;

@Service
public class MediaService {

    public final String uploadDir;
    private final Tika tika;
    private final MediaRepository mediaRepository;

    public MediaService(@Value("${app.upload.dir:${user.home}}") String uploadDir, PaymentRepository paymentRepository, MediaRepository mediaRepository) {
        this.uploadDir = uploadDir;
        this.mediaRepository = mediaRepository;
        this.tika = new Tika();
    }

    private final Map<String, String> supportedMediaTypeExtensions = Map.of(
            MediaType.IMAGE_PNG_VALUE, ".png",
            MediaType.IMAGE_JPEG_VALUE, ".jpg"
    );

//    public MediaUploadDto downloadImage(MultipartFile file) {
//        try {
//            final var type = tika.detect(file.getInputStream());
//            final var extension = Optional.ofNullable(
//                    supportedMediaTypeExtensions.get(type)
//            ).orElseThrow(UnsupportedMediaTypeException::new);
//
//            final var uuid = UUID.randomUUID();
//            final var name = uuid + extension;
//
//            final var path = Path.of(uploadDir);
//            final var webPath = "files/";
//            final var resolved = path.resolve(webPath);
//            file.transferTo(resolved.resolve(name));
//            return new MediaUploadDto(uuid, webPath, name);
//        } catch (IOException e) {
//            throw new MediaUploadException(e);
//        }
//    }

//    public InputStream getImage(String path) {
//        try {
//            return FileUtils.openInputStream
//                    (new File(path));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public byte[] uploadImage(UUID id) {
//        FileModel file = getPaymentFile(id);
//        InputStream content = getImage(file.getUrl());
//        try {
//            return IOUtils.toByteArray(content);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private FileModel getPaymentFile(UUID id) {
//        MediaUploadEntity mediaEntity = mediaRepository.findById(id).orElseThrow(PaymentNotFoundExeption::new);
//        return new FileModel(
//                mediaEntity.getId(),
//                mediaEntity.getWebPath(),
//                mediaEntity.getName()
//        );
//    }


}
