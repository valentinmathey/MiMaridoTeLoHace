package com.egg.MiMaridoTeLoHace.Controllers;
import com.egg.MiMaridoTeLoHace.Entities.Image;
import com.egg.MiMaridoTeLoHace.Services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/image")
public class ImageController {
    @Autowired
    ImageService imageService;
    //el DB se usa para diferenciar el local de la base de datos
    @GetMapping("/DB/{id}")
    @ResponseBody
    public byte[] getImageById(@PathVariable("id") String id) {
        Image image = imageService.GetById(id);
        return image.getContent();
    }

    @GetMapping("/{name}")
    @ResponseBody
    public ResponseEntity<Resource> getImageLocal(@PathVariable("name") String name){
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:/static/img/" + name);
        try {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, String.valueOf(MediaType.parseMediaType("image/jpg, image/jpeg, image/png")))
                    .contentLength(resource.contentLength())
                    .body(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
