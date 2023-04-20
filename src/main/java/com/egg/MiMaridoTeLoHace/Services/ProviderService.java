package com.egg.MiMaridoTeLoHace.Services;

import javax.transaction.Transactional;

import com.egg.MiMaridoTeLoHace.Entities.Image;
import com.egg.MiMaridoTeLoHace.Exceptions.MiException;
import com.egg.MiMaridoTeLoHace.Entities.Provider;
import com.egg.MiMaridoTeLoHace.Enums.Locations;
import com.egg.MiMaridoTeLoHace.Enums.Professions;
import com.egg.MiMaridoTeLoHace.Enums.Roles;
import com.egg.MiMaridoTeLoHace.Repositories.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProviderService implements UserDetailsService {
    
    @Autowired
    ProviderRepository providerRepository;
    @Autowired
    ImageService imageService;

    @Transactional
    public void createProvider(Provider provider) throws MiException {

        validateData(provider.getName(), provider.getEmail(), provider.getPriceTime(), provider.getProfession());


        //eric: lo cambie para que sea mas compacto, en ves de estar igualando
        // los valores que recibe solo modifica el que necesita ser modificado
        Provider providerSave = provider;

        providerSave.setPassword(new BCryptPasswordEncoder().encode(provider.getPassword()));

        provider.setRole(Roles.PROVIDER);

        //eric: se le asigna de forma random una calificacion del 1 al 5 con random (Temporal)
        provider.setRaiting((int) (Math.random() * 5 + 1));

        providerRepository.save(provider);
    }


    
    @Transactional
    public void deleteProvider(String id) throws Exception{ 
        try {
            Provider provider = providerRepository.findById(id).get();
        
            providerRepository.delete(provider);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    @Transactional
    public void modifyProvider(String name, String email, int priceTime, Professions profession) throws MiException {
        validateData(name, email, priceTime, profession);
        Provider provider = findProviderByEmail(email);
        if (provider == null) {
            throw new MiException("Provider no enocntrado");
        } else {
            provider.setName(name);
            provider.setEmail(email);
            provider.setPriceTime(priceTime);
            provider.setProfession(profession);
        }
    }

    public Provider getById(String id){
        return providerRepository.findById(id).get();
    }
    public Provider findProviderByEmail(String email) {
        return providerRepository.searchByEmail(email);
    }
    
    public List<Provider> searchLocationAndProfession(String location, String profession){
        try {
            for (Locations lo : Locations.values()) {
                if (lo.name().equals(location)) {
                    for (Professions pr : Professions.values()) {
                        if (pr.name().equals(profession)) {
                            return providerRepository.searchByLocationAndProfession(lo, pr);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Provider> searchProfession(String search) throws Exception {
        try {
            for (Professions pr : Professions.values()) {
                if (pr.name().equals(search)) {
                    return providerRepository.searchByProfession(pr);
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return null;
    }

    public List<Provider> searchLocation(String search) throws Exception {
        try {
            for (Locations lo : Locations.values()) {
                if (lo.name().equals(search)) {
                    return providerRepository.searchByLocation(lo);
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return null;
    }

    public List<Provider> getAll() throws MiException {
        try {
            return providerRepository.findAll();
        } catch (Exception e) {
            throw new MiException("LISTA VACIA O NULA");
        }
    }

    @Transactional
    public void save(Provider p) {
        providerRepository.save(p);
    }

    private void validateData(String name, String email, int priceTime, Professions profession) throws MiException {
        if (name.isEmpty()) {
            throw new MiException("NOMBRE PROVIDER invalido o vacio");
        } else if (email.isEmpty()) {
            throw new MiException("EMAIL invalido o vacio");
        } else if (priceTime == 0) {
            throw new MiException("EL PRECIO es invalido o vacio");
        } else if (profession == null) {
            throw new MiException("DEBE ELEGIR UNA PROFESION");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
