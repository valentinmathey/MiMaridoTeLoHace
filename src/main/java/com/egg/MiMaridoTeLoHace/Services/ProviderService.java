package com.egg.MiMaridoTeLoHace.Services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egg.MiMaridoTeLoHace.Entities.Provider;
import com.egg.MiMaridoTeLoHace.Enums.Professions;
import com.egg.MiMaridoTeLoHace.Exceptions.MiException;
import com.egg.MiMaridoTeLoHace.Repositories.ProviderRepository;

@Service
public class ProviderService {
    
    @Autowired
    private ProviderRepository providerRepositorio;

    @Transactional
    public void createProvider (String name, String email, String password, int priceTime, Professions profession) throws MiException {

        validateData(name, email, priceTime, profession);
        
        Provider provider = new Provider();

        provider.setName(name);
        provider.setEmail(email);
        provider.setPassword(password);
        provider.setProfession(profession);
        provider.setPriceTime(priceTime);
        
        providerRepositorio.save(provider);
    }



    private void validateData (String name, String email, int priceTime, Professions profession) throws MiException {
        if (name.isEmpty() || name==null) {
            throw new MiException("NOMBRE PROVIDER invalido o vacio");
        } else if   (email.isEmpty() || email==null) {
            throw new MiException("EMAIL invalido o vacio");
        } else if   (priceTime==0) {
            throw new MiException("EL PRECIO es invalido o vacio");
        } else if   (profession==null) {
            throw new MiException("DEBE ELEGIR UNA PROFESION");
        } 
    }

}