package ch.heigvd.sprint0.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ImageController {
    @Value("${server.tomcat.upload-dir}")
    private String uploadPath;

    @GetMapping(
            value = "/image/{name}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody byte[] getImageWithMediaType(@PathVariable(value="name") String imageName) throws IOException {
        Path destinationFile = Paths.get(uploadPath).resolve(
                Paths.get(imageName)).normalize().toAbsolutePath();

        return Files.readAllBytes(destinationFile);
    }
}
