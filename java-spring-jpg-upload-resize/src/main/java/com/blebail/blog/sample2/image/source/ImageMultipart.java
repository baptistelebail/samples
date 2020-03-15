package com.blebail.blog.sample2.image.source;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public final class ImageMultipart implements ImageSource {

    private final MultipartFile multipartFile;

    public ImageMultipart(MultipartFile multipartFile) {
        this.multipartFile = Objects.requireNonNull(multipartFile);
    }

    @Override
    public File asFile() throws IOException {
        File imageFile = Files.createTempFile("image_upload_", ".tmp")
                .toFile();

        multipartFile.transferTo(imageFile);

        return imageFile;
    }
}
