package com.egg.MiMaridoTeLoHace.Services;

import javax.transaction.Transactional;
import com.egg.MiMaridoTeLoHace.Exceptions.MiException;
import com.egg.MiMaridoTeLoHace.Entities.Provider;
import com.egg.MiMaridoTeLoHace.Enums.Locations;
import com.egg.MiMaridoTeLoHace.Enums.Professions;
import com.egg.MiMaridoTeLoHace.Enums.Roles;
import com.egg.MiMaridoTeLoHace.Repositories.ProviderRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class ProviderService implements UserDetailsService {
    
    @Autowired
    private ProviderRepository providerRepository;

    @Transactional
    public void createProvider(String name, String email, String password, int priceTime, Professions profession) throws MiException {

        validateData(name, email, priceTime, profession);

        Provider provider = new Provider();

        provider.setName(name);
        provider.setEmail(email);
        provider.setPassword(new BCryptPasswordEncoder().encode(password));
        provider.setProfession(profession);
        provider.setPriceTime(priceTime);

        provider.setRole(Roles.PROVIDER);

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

    public Provider searchById(String id){
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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Provider provider = providerRepository.searchByEmail(email);

        if (provider != null) {

            List<GrantedAuthority> authorities = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + provider.getRole().toString());

            authorities.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("providersession", provider);

            return (UserDetails) new User(provider.getEmail(), provider.getPassword(), authorities);
        } else {
            return null;
        }
    }
}
