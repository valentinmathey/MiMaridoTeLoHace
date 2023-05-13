package com.egg.MiMaridoTeLoHace.Services;

import com.egg.MiMaridoTeLoHace.Entities.Image;
import com.egg.MiMaridoTeLoHace.Repositories.ImageRepository;
import com.egg.MiMaridoTeLoHace.converters.ImageConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    ImageConverter imageConverter;

    @Transactional
    public void Save(Image image){
       imageRepository.save(image);
    }

    @Transactional
    public Image Update(MultipartFile archivo, String idImagen){
        if (archivo != null) {
            try {
                Image imagen = new Image();
                if (idImagen != null) {
                    Optional<Image> respuesta = imageRepository.findById(idImagen);

                    if (respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }
                }

                imagen.setMime(archivo.getContentType());

                imagen.setName(archivo.getName());

                imagen.setContent(archivo.getBytes());

                return imageRepository.save(imagen);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
    public Image GetById(String id){
        return imageRepository.findById(id).get();
    }

    public Image GetByName(String name) throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:/static/img/" + name);
        return imageConverter.ResourcetoImage(resource);
    }

    @Transactional
    public void Delete(String id){
        imageRepository.delete(imageRepository.findById(id).get());
    }
}
