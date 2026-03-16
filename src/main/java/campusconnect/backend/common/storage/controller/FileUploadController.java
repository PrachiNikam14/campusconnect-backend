package campusconnect.backend.common.storage.controller;

import campusconnect.backend.common.storage.dto.FileUploadResponse;
import campusconnect.backend.common.storage.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @PostMapping("/upload")
    public FileUploadResponse uploadFile(
            @RequestParam MultipartFile file,
            @RequestParam String folder
    ) {

        return fileUploadService.uploadFile(file, folder);

    }
}