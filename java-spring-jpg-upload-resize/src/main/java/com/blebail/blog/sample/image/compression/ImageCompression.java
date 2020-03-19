package com.blebail.blog.sample.image.compression;

import com.blebail.blog.sample.image.source.ImageSource;

public interface ImageCompression {

    void compress(ImageSource imageSource, String imageName);
}
