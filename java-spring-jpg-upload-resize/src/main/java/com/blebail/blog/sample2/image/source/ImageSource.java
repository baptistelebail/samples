package com.blebail.blog.sample2.image.source;

import java.io.File;
import java.io.IOException;

public interface ImageSource {

    File asFile() throws IOException;
}
