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
    public void deleteProvider(String id) throws Exception {
        try {
            Provider provider = providerRepository.getById(id);

            providerRepository.delete(provider);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    public List<Provider> searchLocationAndProfession(String location, String profession) throws Exception {
        try {

            switch (profession) {
                case "GASISTA":
                    if (location.equals("BARRIO_1")) {
                        return providerRepository.searchByLocationAndProfession(Locations.BARRIO_1,
                                Professions.GASISTA);
                    }
                    if (location.equals("BARRIO_2")) {
                        return providerRepository.searchByLocationAndProfession(Locations.BARRIO_2,
                                Professions.GASISTA);
                    }
                    if (location.equals("BARRIO_3")) {
                        return providerRepository.searchByLocationAndProfession(Locations.BARRIO_3,
                                Professions.GASISTA);
                    }

                case "ELECTRICISTA":
                    if (location.equals("BARRIO_1")) {
                        return providerRepository.searchByLocationAndProfession(Locations.BARRIO_1,
                                Professions.GASISTA);
                    }
                    if (location.equals("BARRIO_2")) {
                        return providerRepository.searchByLocationAndProfession(Locations.BARRIO_2,
                                Professions.GASISTA);
                    }
                    if (location.equals("BARRIO_3")) {
                        return providerRepository.searchByLocationAndProfession(Locations.BARRIO_3,
                                Professions.GASISTA);
                    }

                case "PLOMERO":
                    if (location.equals("BARRIO_1")) {
                        return providerRepository.searchByLocationAndProfession(Locations.BARRIO_1,
                                Professions.GASISTA);
                    }
                    if (location.equals("BARRIO_2")) {
                        return providerRepository.searchByLocationAndProfession(Locations.BARRIO_2,
                                Professions.GASISTA);
                    }
                    if (location.equals("BARRIO_3")) {
                        return providerRepository.searchByLocationAndProfession(Locations.BARRIO_3,
                                Professions.GASISTA);
                    }

                case "LIMPIEZA":
                    if (location.equals("BARRIO_1")) {
                        return providerRepository.searchByLocationAndProfession(Locations.BARRIO_1,
                                Professions.GASISTA);
                    }
                    if (location.equals("BARRIO_2")) {
                        return providerRepository.searchByLocationAndProfession(Locations.BARRIO_2,
                                Professions.GASISTA);
                    }
                    if (location.equals("BARRIO_3")) {
                        return providerRepository.searchByLocationAndProfession(Locations.BARRIO_3,
                                Professions.GASISTA);
                    }

                case "CERRAJERO":
                    if (location.equals("BARRIO_1")) {
                        return providerRepository.searchByLocationAndProfession(Locations.BARRIO_1,
                                Professions.GASISTA);
                    }
                    if (location.equals("BARRIO_2")) {
                        return providerRepository.searchByLocationAndProfession(Locations.BARRIO_2,
                                Professions.GASISTA);
                    }
                    if (location.equals("BARRIO_3")) {
                        return providerRepository.searchByLocationAndProfession(Locations.BARRIO_3,
                                Professions.GASISTA);
                    }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Provider> searchLocation(String search) throws Exception {
        try {
            switch (search) {
                case "BARRIO_1":
                    return providerRepository.searchByLocation(Locations.BARRIO_1);
                case "BARRIO_2":
                    return providerRepository.searchByLocation(Locations.BARRIO_1);
                case "BARRIO_3":
                    return providerRepository.searchByLocation(Locations.BARRIO_1);
                default:
                    return null;
            }

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
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

    public void modifyProvider(String name, String email, int priceTime, Professions profession) throws MiException {
        validateData(name, email, priceTime, profession);
        Provider provider = findProviderByEmail(email);
        if (provider == null) {
            throw new MiException("Provider not found");
        } else {
            provider.setName(name);
            provider.setEmail(email);
            provider.setPriceTime(priceTime);
            provider.setProfession(profession);
        }
    }

    public Provider findProviderByEmail(String email) {
        List<Provider> providerList = providerRepository.findAll();
        for (Provider provider : providerList) {
            if (provider.getEmail().equals(email)) {
                return provider;
            }
        }
        return null;
    }
}
