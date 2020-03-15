package com.blebail.blog.sample2.image.compression;

import com.blebail.blog.sample2.image.source.ImageSource;

public interface ImageCompression {

    void compress(ImageSource imageSource, String imageName);
}
