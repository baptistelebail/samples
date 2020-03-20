package com.blebail.blog.sample.image.compression;

import com.blebail.blog.sample.image.format.SquareCompressed;
import com.blebail.blog.sample.image.source.ImageSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class JpgImageCompression implements ImageCompression {

    private String imagesPathAsString;

    @Value("${images.path}" )
    public void setImagesPathAsString(String imagesPathAsString) {
        this.imagesPathAsString = imagesPathAsString;
    }

    @Override
    public void compress(ImageSource imageSource, String imageName) {
        try {
            Path imagesPath = Files.createDirectories(Paths.get(imagesPathAsString));
            String compressedImageFileName = imageName + "." + JpgImage.EXTENSION;
            File compressedImageFile = imagesPath.resolve(compressedImageFileName)
                    .toFile();

            File imageSourceFile = imageSource.asFile();

            new JpgImage(imageSourceFile, new SquareCompressed(200))
                    .compressTo(compressedImageFile);

            imageSourceFile.delete();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
