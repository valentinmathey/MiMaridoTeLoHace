package com.egg.MiMaridoTeLoHace.Services;

import javax.transaction.Transactional;
import com.egg.MiMaridoTeLoHace.Exceptions.MiException;
import com.egg.MiMaridoTeLoHace.Entities.Provider;
import com.egg.MiMaridoTeLoHace.Enums.Locations;
import com.egg.MiMaridoTeLoHace.Enums.Professions;
import com.egg.MiMaridoTeLoHace.Enums.Roles;
import com.egg.MiMaridoTeLoHace.Repositories.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProviderService {
    @Autowired
    private ProviderRepository providerRepository;

    @Transactional
    public void createProvider(String name, String email, String password, int priceTime, Professions profession) throws MiException {

        validateData(name, email, priceTime, profession);

        Provider provider = new Provider();

        provider.setName(name);
        provider.setEmail(email);
        provider.setPassword(password);
        provider.setProfession(profession);
        provider.setPriceTime(priceTime);

        provider.setRole(Roles.PROVIDER);

        providerRepository.save(provider);
    }
    
    @Transactional
    public void deleteProvider(String id) throws Exception{ 
        try {
            Provider provider = providerRepository.getById(id);
        
            providerRepository.delete(provider);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    public List<Provider> searchLocationAndProfession(String location, String profession) throws Exception {
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

    public List<Provider> getAll() throws Exception {
        try {
            return providerRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void save(Provider p) {
        providerRepository.save(p);
    }

    private void validateData(String name, String email, int priceTime, Professions profession) throws MiException {
        if (name.isEmpty() || name == null) {
            throw new MiException("NOMBRE PROVIDER invalido o vacio");
        } else if (email.isEmpty() || email == null) {
            throw new MiException("EMAIL invalido o vacio");
        } else if (priceTime == 0) {
            throw new MiException("EL PRECIO es invalido o vacio");
        } else if (profession == null) {
            throw new MiException("DEBE ELEGIR UNA PROFESION");
        }
    }
}
