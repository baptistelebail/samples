package com.blebail.blog.sample2.image.compression;

import com.blebail.blog.sample2.image.format.ImageFormat;
import com.blebail.blog.sample2.image.format.SquareHighlyCompressed;
import com.blebail.blog.sample2.image.source.ImageSource;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.NoSuchElementException;

@Component
public class JpgImageCompression implements ImageCompression {

    private static final String JPG_EXTENSION = "jpg";

    @Value("${images.path}")
    private String imagesPathAsString;

    private final ImageFormat format = new SquareHighlyCompressed();

    @Override
    public void compress(ImageSource imageSource, String imageName) {
        try {
            Path imagesPath = Files.createDirectories(Paths.get(this.imagesPathAsString));
            File imageSourceFile = imageSource.asFile();
            String compressedImageFileName = imageName + "." + JPG_EXTENSION;
            File compressedImageFile = imagesPath.resolve(compressedImageFileName)
                    .toFile();

            compressFiles(imageSourceFile, compressedImageFile);

            imageSourceFile.delete();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void compressFiles(File source, File target) throws IOException {
        FileImageOutputStream targetOutputStream = new FileImageOutputStream(target);
        BufferedImage resizedImage = resize(source);

        ImageWriter writer = getWriter();
        ImageWriteParam writerSettings = getWriterSettings(writer);

        try {
            writer.setOutput(targetOutputStream);
            writer.write(null, new IIOImage(resizedImage, null, null), writerSettings);
        } finally {
            writer.dispose();
            targetOutputStream.close();
            resizedImage.flush();
        }
    }

    private BufferedImage resize(File imageFile) throws IOException {
        BufferedImage sourceImage = ImageIO.read(imageFile);

        return Scalr.resize(sourceImage, Scalr.Mode.FIT_EXACT, format.width(), format.height());
    }

    private ImageWriter getWriter() {
        Iterator<ImageWriter> imageWritersIterator = ImageIO.getImageWritersByFormatName(JPG_EXTENSION);

        if (!imageWritersIterator.hasNext()) {
            throw new NoSuchElementException(String.format("Could not find an image writer for %s format", JPG_EXTENSION));
        }

        return imageWritersIterator.next();
    }

    private ImageWriteParam getWriterSettings(ImageWriter imageWriter) {
        ImageWriteParam imageWriteParams = imageWriter.getDefaultWriteParam();

        imageWriteParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        imageWriteParams.setCompressionQuality(format.compression());

        return imageWriteParams;
    }
}
