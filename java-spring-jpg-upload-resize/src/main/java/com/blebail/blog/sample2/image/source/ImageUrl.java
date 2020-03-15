package com.blebail.blog.sample2.image.source;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.util.Objects;

public final class ImageUrl implements ImageSource {

    private final URL url;

    public ImageUrl(URL url) {
        this.url = Objects.requireNonNull(url);
    }

    @Override
    public File asFile() throws IOException {
        File imageFile = Files.createTempFile("image-url", ".tmp")
                .toFile();

        FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
        ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());

        fileOutputStream.getChannel()
                .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);

        return imageFile;
    }
}
