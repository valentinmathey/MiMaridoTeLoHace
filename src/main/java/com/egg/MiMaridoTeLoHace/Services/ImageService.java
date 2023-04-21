package com.egg.MiMaridoTeLoHace.Services;

import com.egg.MiMaridoTeLoHace.Entities.Image;
import com.egg.MiMaridoTeLoHace.Exceptions.MiException;
import com.egg.MiMaridoTeLoHace.Repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ImageService {
    //eric: se creo el crud completo de las imagenes falta su prueba e implementacion
    @Autowired
    private ImageRepository imageRepository;
    //se removio el convertMultipartToImage por que no se usaba
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

    public Image GetByName(String name){
        return imageRepository.getByName(name);
    }

    @Transactional
    public void Delete(String id){
        imageRepository.delete(imageRepository.findById(id).get());
    }
}
