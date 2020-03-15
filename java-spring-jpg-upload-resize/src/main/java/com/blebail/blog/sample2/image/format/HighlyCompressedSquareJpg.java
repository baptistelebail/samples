package com.blebail.blog.sample2.image.format;

public final class HighlyCompressedSquareJpg implements ImageFormat {

    @Override
    public int width() {
        return 200;
    }

    @Override
    public int height() {
        return 200;
    }

    @Override
    public String extension() {
        return "jpg";
    }

    @Override
    public float compression() {
        return 0.90f;
    }
}
