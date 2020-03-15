package com.blebail.blog.sample2.image.compression;

import com.blebail.blog.sample2.image.format.HighlyCompressedSquareJpg;
import com.blebail.blog.sample2.image.source.ImageSource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class JpgImageCompression implements ImageCompression {

    private final Path compressedJpgPath;

    public JpgImageCompression() throws IOException {
        this.compressedJpgPath = Files.createTempDirectory("jpg-compression");
    }

    @Override
    public void compress(ImageSource imageSource) {
        try {
            File imageSourceFile = imageSource.asFile();
            File compressedImageFile = compressedJpgPath.resolve(imageSourceFile.getName()).toFile();

            new JpgImage(imageSourceFile, new HighlyCompressedSquareJpg())
                    .compressTo(compressedImageFile);

            imageSourceFile.delete();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
