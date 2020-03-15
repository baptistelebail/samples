package com.blebail.blog;

import com.blebail.blog.sample2.Application;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
public class ImageUploadTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldUploadResizeAndCompressJpgFile() throws Exception {
        Path jpgImagePath = Paths.get(getClass().getResource("/image.jpg").toURI());
        MockMultipartFile jpgImageMultipartFile = new MockMultipartFile("file", "image.jpg", "image/jpeg", Files.readAllBytes(jpgImagePath));

        mockMvc.perform(multipart("/image/upload/file")
                .file(jpgImageMultipartFile))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUploadResizeAndCompressJpgFromUrl() throws Exception {
        URL imageUrl = getClass().getResource("/image.jpg");

        mockMvc.perform(post("/image/upload/url")
                .content(imageUrl.toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnHttpBadRequestWhenURLIsNotValid() throws Exception {
        String invalidUrl = "thisIsNotAValidUrl";

        mockMvc.perform(post("/image/upload/url")
                .content(invalidUrl))
                .andExpect(status().isBadRequest());
    }
}
