package com.blebail.blog.sample2.image.format;

public final class SquareHighlyCompressed implements ImageFormat {

    private final int size;

    public SquareHighlyCompressed(int size) {
        this.size = size;
    }

    @Override
    public int width() {
        return size;
    }

    @Override
    public int height() {
        return size;
    }

    @Override
    public float compression() {
        return 0.90f;
    }
}
