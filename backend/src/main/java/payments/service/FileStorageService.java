package payments.service;

import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import payments.models.FileModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class FileStorageService {
    private Path path;

    private Map<Integer, FileModel> storage = new HashMap<>();
    private int id = 0;

    public FileStorageService(@Value("${app.upload.dir:${user.home}}") String uploadDir) {
        path = Path.of(uploadDir);
        path.toFile().deleteOnExit();
    }

    @SuppressWarnings("unused")
    public Collection<FileModel> getList() {
        return new ArrayList<>(storage.values());
    }

    public void delete(Integer id) {
        if (id != null) {
            FileModel model = storage.remove(id);
            if (model != null) {
                try {
                    var filePath = path.resolve(model.getFileName()).normalize();
                    Files.deleteIfExists(filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Synchronized
    public FileModel storeFile(MultipartFile file) {
        try {
            var fileName = StringUtils.cleanPath(file.getOriginalFilename());

            var targetLocation = path.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            targetLocation.toFile().deleteOnExit();

            var uri = ServletUriComponentsBuilder.fromPath(fileName).toUriString();
            var model = new FileModel((long) ++id, fileName, uri);
            storage.put(id, model);

            return model;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Resource loadFile(String fileName) {
        try {
            var filePath = path.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
