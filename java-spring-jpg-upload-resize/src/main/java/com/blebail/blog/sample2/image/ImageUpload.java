package com.blebail.blog.sample2.image;

import com.blebail.blog.sample2.image.compression.ImageCompression;
import com.blebail.blog.sample2.image.source.ImageMultipart;
import com.blebail.blog.sample2.image.source.ImageUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;
import java.net.URL;

@Controller
public class ImageUpload {

    private final ImageCompression imageCompression;

    @Autowired
    public ImageUpload(ImageCompression imageCompression) {
        this.imageCompression = imageCompression;
    }

    @PostMapping(value = "image/upload/file")
    @ResponseStatus(HttpStatus.OK)
    public void uploadJpgImageFile(@RequestPart(value = "file") MultipartFile multipartFile) {
        imageCompression.compress(new ImageMultipart(multipartFile));
    }

    @PostMapping(value = "image/upload/url")
    @ResponseStatus(HttpStatus.OK)
    public void uploadJpgImageUrl(@RequestBody String urlAsString) {
        URL url;

        try {
            url = new URL(urlAsString);
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        imageCompression.compress(new ImageUrl(url));
    }
}
