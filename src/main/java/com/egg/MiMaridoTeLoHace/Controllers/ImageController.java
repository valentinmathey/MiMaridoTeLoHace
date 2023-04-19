package com.egg.MiMaridoTeLoHace.Controllers;

import com.egg.MiMaridoTeLoHace.Entities.Customer;
import com.egg.MiMaridoTeLoHace.Entities.Provider;
import com.egg.MiMaridoTeLoHace.Services.CustomerService;
import com.egg.MiMaridoTeLoHace.Services.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ImageController {

    @Autowired
    CustomerService customerService;

    @Autowired
    ProviderService providerService;

    //eric: fotos para el perfil queda ver como usarlas (posiblemente enviandolas con el ModelMap)
    @GetMapping("/Customer/{id}")
    public ResponseEntity<byte[]> customerImage (@PathVariable String id){
        Customer customer = customerService.searchById(id);

        byte[] image = customer.getImage().getContent();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(image,headers, HttpStatus.OK);
    }

    @GetMapping("/Provider/{id}")
    public ResponseEntity<byte[]> providerImage (@PathVariable String id){
        Provider provider = providerService.searchById(id);

        byte[] image = provider.getImage().getContent();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(image,headers, HttpStatus.OK);
    }

    //eric: fotos de los html

}
