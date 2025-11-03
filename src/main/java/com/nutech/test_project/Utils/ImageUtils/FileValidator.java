package com.nutech.test_project.Utils.ImageUtils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.util.Arrays;

@Component
public class FileValidator {

    private final String[] allowedImageExtensions = {
            "jpeg", "png"
    };

    public boolean isValidFileExtension(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase();
        return Arrays.asList(allowedImageExtensions).contains(fileExtension);
    }
}