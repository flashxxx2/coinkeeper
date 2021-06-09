package payments.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import payments.criteria.FileCriteria;
import payments.entity.FileUploadEntity;
import payments.exception.PaymentNotFoundException;
import payments.mapper.Mapper;
import payments.models.FileModel;
import payments.repository.MediaRepository;
import payments.repository.PaymentRepository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Service
public class MediaService {

    public final String uploadDir;
    private final PaymentRepository paymentRepository;
    private final MediaRepository mediaRepository;

    public MediaService(@Value("${app.upload.dir:${user.home}}") String uploadDir, PaymentRepository paymentRepository, MediaRepository mediaRepository) {
        this.uploadDir = uploadDir;
        this.paymentRepository = paymentRepository;
        this.mediaRepository = mediaRepository;
    }

    private final Map<String, String> supportedMediaTypeExtensions = Map.of(
            MediaType.IMAGE_PNG_VALUE, ".png",
            MediaType.IMAGE_JPEG_VALUE, ".jpg"
    );

    public InputStream getImage(String path) {
        try {
            return FileUtils.openInputStream
                    (new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] uploadImage(Long id) {
        FileModel file = getPaymentFile(id);
        InputStream content = getImage(file.getUrl());
        try {
            return IOUtils.toByteArray(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private FileModel getPaymentFile(Long id) {
        FileUploadEntity fileUploadEntity = mediaRepository.findById(id).orElseThrow(PaymentNotFoundException::new);
        return new FileModel(
                fileUploadEntity.getId(),
                fileUploadEntity.getName(),
                fileUploadEntity.getUrl()
        );
    }

    public Page<FileModel> getPaymentImages(Long id, FileCriteria criteria) {
        final var entity = Optional.ofNullable(paymentRepository.getById(id))
                .orElseThrow(PaymentNotFoundException::new);
        PageRequest pageRequest = PageRequest.of(criteria.getPageNumber(), criteria.getPageSize(), criteria.getSort());
        Page<FileModel> fileModelPage = mediaRepository.findAllByPayment(entity, pageRequest)
                .map(Mapper::convertToFileModel);
        return fileModelPage;
    }
}
