package payments.service.api;

import payments.models.FileModel;

import java.io.InputStream;
import java.util.List;

public interface MediaService {


    InputStream getImage(String path);

    byte[] uploadImage(Long id);

    List<FileModel> getPaymentFile(Long id);

    List<FileModel> getPaymentImages(Long id);

    void deleteUnMappedFiles();
}
