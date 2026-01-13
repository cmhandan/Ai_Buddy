package help.buddy.ai.backend.services;

import help.buddy.ai.backend.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    void storeFile(MultipartFile file, User user);
}
