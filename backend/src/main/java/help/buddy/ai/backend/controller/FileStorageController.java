package help.buddy.ai.backend.controller;
import help.buddy.ai.backend.entity.User;
import help.buddy.ai.backend.repository.UserRepository;
import help.buddy.ai.backend.services.FileStorageService;
import help.buddy.ai.backend.utility.Apiresponse;
import help.buddy.ai.backend.utility.Apistatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/document")
public class FileStorageController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/upload")
    public ResponseEntity<Apiresponse> uploadFile(@RequestParam("file") MultipartFile file, Authentication authentication) {
        try {
            // Get Current User
            // Note: Ensure your Security Config sets the authentication principal correctly
            String email = authentication != null ? authentication.getName() : "test@example.com"; // Fallback for testing
            User user = userRepository.findByEmail(email);

            if (user == null) {
                return new ResponseEntity<>(new Apiresponse(null, Apistatus.FAILED, "User not found"), HttpStatus.UNAUTHORIZED);
            }

            fileStorageService.storeFile(file, user);

            return new ResponseEntity<>(new Apiresponse(null, Apistatus.SUCCESS, "File processed and ingested for AI."), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new Apiresponse(null, Apistatus.FAILED, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}