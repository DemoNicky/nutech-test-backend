package com.nutech.test_project.Utils.ImageUtils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UploadUtils {

    private final Cloudinary cloudinary;

    private static final long MAX_FILE_SIZE_BYTES = 3L * 1024 * 1024;

    public String uploadImageResult(MultipartFile file) throws IOException {
        String fileType = file.getContentType();

        if (file.getSize() > MAX_FILE_SIZE_BYTES){
            throw new IllegalArgumentException("File size exceeds the maximum allowed limit of 2 MB.");
        }

        Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        String fileUrl = result.get("url").toString();

        return fileUrl;
    }

}
