package com.blebail.blog.sample.image.source;

import java.io.File;
import java.io.IOException;

public interface ImageSource {

    File asFile() throws IOException;
}
