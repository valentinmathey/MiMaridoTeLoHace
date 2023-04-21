package com.egg.MiMaridoTeLoHace.Controllers;
import com.egg.MiMaridoTeLoHace.Entities.Image;
import com.egg.MiMaridoTeLoHace.Services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@Controller
@RequestMapping("/image")
public class ImageController {
    @Autowired
    ImageService imageService;
    //el DB se usa para diferenciar el local de la base de datos
    @GetMapping("/DB/{name}")
    @ResponseBody
    public byte[] getImage(@PathVariable("name") String name) {
        Image image = imageService.GetByName(name);
        //eric: devolver imagen como arreglo de bytes
        return image.getContent();
    }
}
