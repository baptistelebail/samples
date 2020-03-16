package com.blebail.blog.sample2;

import com.blebail.blog.sample2.image.compression.JpgImageCompression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
public class ImageUploadTest {

    @TempDir
    public Path tempDir;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JpgImageCompression jpgImageCompression;

    @BeforeEach
    void setUp() {
        jpgImageCompression.setImagesPathAsString(tempDir.toString());
    }

    @Test
    public void shouldUploadResizeAndCompressJpgFile() throws Exception {
        Path sourceImagePath = Paths.get(getClass().getResource("/image.jpg").toURI());
        MockMultipartFile sourceImageMultipartFile =
                new MockMultipartFile("file", "image.jpg", "image/jpeg",
                        Files.readAllBytes(sourceImagePath));

        mockMvc.perform(multipart("/image/upload/file/myimage")
                .file(sourceImageMultipartFile))
                .andExpect(status().isOk());

        Path compressedImagePath = tempDir.resolve("myimage.jpg");

        assertThat(Files.exists(compressedImagePath))
                .isTrue();

        assertThat(Files.size(compressedImagePath))
                .isLessThan(Files.size(sourceImagePath));
    }

    @Test
    public void shouldUploadResizeAndCompressJpgFromUrl() throws Exception {
        Path sourceImagePath = Paths.get(getClass().getResource("/image.jpg").toURI());
        URL sourceImageUrl = sourceImagePath.toUri().toURL();

        mockMvc.perform(post("/image/upload/url/myimage")
                .content(sourceImageUrl.toString()))
                .andExpect(status().isOk());

        Path compressedImagePath = tempDir.resolve("myimage.jpg");

        assertThat(Files.exists(compressedImagePath))
                .isTrue();

        assertThat(Files.size(compressedImagePath))
                .isLessThan(Files.size(sourceImagePath));
    }

    @Test
    public void shouldReturnHttpBadRequestWhenURLIsNotValid() throws Exception {
        String invalidUrl = "thisIsNotAValidUrl";

        mockMvc.perform(post("/image/upload/url/myimage")
                .content(invalidUrl))
                .andExpect(status().isBadRequest());
    }
}
