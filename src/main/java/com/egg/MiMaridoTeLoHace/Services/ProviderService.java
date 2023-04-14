package com.egg.MiMaridoTeLoHace.Services;

import com.egg.MiMaridoTeLoHace.Entities.Provider;
import com.egg.MiMaridoTeLoHace.Enums.Locations;
import com.egg.MiMaridoTeLoHace.Enums.Professions;
import com.egg.MiMaridoTeLoHace.Repositories.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author eric
 */
@Service
public class ProviderService {
    @Autowired
    ProviderRepository PR;

    public List<Provider> searchLocationAndProfession(String location, String profession) throws Exception {
        try {
            Object p = null, l = null;
            for (Locations lo : Locations.values()) {
                if (lo.name().equals(location)) {
                    for (Professions pr : Professions.values()) {
                        if (pr.name().equals(profession)) {
                            return PR.searchByLocationAndProfession(lo, pr);
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
            for (Locations pr : Locations.values()) {
                if (pr.name().equals(search)) {
                    return PR.searchByProfession(pr);
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public List<Provider> searchLocation(String search) throws Exception {
        try {
            for (Locations lo : Locations.values()) {
                if (lo.name().equals(search)) {
                    return PR.searchByLocation(lo);
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public List<Provider> getAll() throws Exception {
        try {
            return PR.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public Provider getEmail(String mail) throws Exception {
        try {
            return PR.searchByMail(mail);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void save(Provider p) {
        PR.save(p);
    }
}
