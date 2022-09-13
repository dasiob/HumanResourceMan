package com.vmo.controller;

import com.vmo.models.response.ImageResponse;
import com.vmo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/v1/file")
public class ImageController {

    @Autowired
    private UserService userService;
    @Value("${project.image}")
    private String path;

    @PostMapping("/upload")
    public ResponseEntity<?> fileUpload(@RequestPart("image") MultipartFile image) {
        String fileName = null;
        try {
            fileName = this.userService.uploadImage(path, image);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ImageResponse(null, "Image failed to upload"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new ImageResponse(fileName, "Image uploaded successfully"), HttpStatus.OK);
    }

    @GetMapping(value = "/getImage/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        InputStream resource = this.userService.getResource(path, fileName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}
