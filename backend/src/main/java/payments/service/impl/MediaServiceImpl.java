package payments.service.impl;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import payments.exception.PaymentImagesNotFoundException;
import payments.exception.PaymentNotFoundException;
import payments.mapper.Mapper;
import payments.models.FileModel;
import payments.repository.MediaRepository;
import payments.repository.PaymentRepository;
import payments.service.api.MediaService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MediaServiceImpl implements MediaService {

    public final String uploadDir;
    private final PaymentRepository paymentRepository;
    private final MediaRepository mediaRepository;

    public MediaServiceImpl(@Value("${app.upload.dir:${user.home}}") String uploadDir, PaymentRepository paymentRepository, MediaRepository mediaRepository) {
        this.uploadDir = uploadDir;
        this.paymentRepository = paymentRepository;
        this.mediaRepository = mediaRepository;
    }

    @Override
    @Cacheable(value = "itemCache")
    public InputStream getImage(String path) {
        try {
            return FileUtils.openInputStream
                    (new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public byte[] uploadImage(Long id) {
        var file = getPaymentFile(id);
        InputStream content = getImage(file.get(0).getUrl());
        try {
            return IOUtils.toByteArray(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<FileModel> getPaymentFile(Long id) {
        var paymentEntity= paymentRepository.findById(id)
                .orElseThrow(PaymentNotFoundException::new);

        var fileUploadEntity = mediaRepository.findAllByPayment(paymentEntity);
        return fileUploadEntity.stream().map(entity -> new FileModel(
                entity.getId(),
                entity.getName(),
                entity.getUrl())).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "itemCache")
    public List<FileModel> getPaymentImages(Long id) {
        final var entity = Optional.ofNullable(paymentRepository.getById(id))
                .orElseThrow(PaymentImagesNotFoundException::new);

        return mediaRepository.findAllByPayment(entity)
                .stream()
                .map(Mapper::convertToFileModel)
                .collect(Collectors.toList());
    }

    @Override
    @Scheduled(cron = "0 22 * * * *")
    public void deleteUnMappedFiles() {
        mediaRepository.deleteUnMappedRows();
    }
}
