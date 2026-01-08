package help.buddy.ai.backend.controller;

import help.buddy.ai.backend.utility.Apiresponse;
import help.buddy.ai.backend.utility.Apistatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/document")
public class FileStorageController {
    
    private final String PIPE = "/";
    
    @PostMapping("upload")
    public ResponseEntity<Apiresponse> uploadFile(@RequestParam("file")MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>(new Apiresponse(null, Apistatus.FAILED, "File Not Found"), HttpStatus.EXPECTATION_FAILED);
        }
        try {
            // 2. Define the path where you want to save the file
            String uploadDir = "public"; // Or use a relative path
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            String absolutePath = directory.getAbsoluteFile() + PIPE +  file.getOriginalFilename();
            // 3. Save the file to the target location
            file.transferTo(new File(absolutePath));

            return new ResponseEntity<>(new Apiresponse(null, Apistatus.SUCCESS, "File uploaded successfully" + absolutePath), HttpStatus.CREATED);

        } catch (IOException e) {
            return new ResponseEntity<>(new Apiresponse(null, Apistatus.FAILED, e.getMessage()), HttpStatus.EXPECTATION_FAILED);
        }
        
    }
}
