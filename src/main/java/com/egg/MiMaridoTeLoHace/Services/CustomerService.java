package com.egg.MiMaridoTeLoHace.Services;

import com.egg.MiMaridoTeLoHace.Entities.Customer;
import com.egg.MiMaridoTeLoHace.Entities.Image;
import com.egg.MiMaridoTeLoHace.Enums.Locations;
import com.egg.MiMaridoTeLoHace.Enums.Roles;
import com.egg.MiMaridoTeLoHace.Exceptions.MiException;
import com.egg.MiMaridoTeLoHace.Repositories.CustomerRepository;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class CustomerService implements UserDetailsService {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ImageService imageService;
    @Transactional
    public void createCustomer(Customer customer, Image image) throws MiException {
        
        validateData(customer.getName(), customer.getEmail(), customer.getLocation());

        try {
            customer.setImage(image.getId());
            customer.setRole(Roles.CUSTOMER);
            customer.setPassword(new BCryptPasswordEncoder().encode(customer.getPassword()));
            customerRepository.save(customer);

        } catch (Exception e) {
            throw new MiException("ERROR al crear nuevo Usuario");
        }
    }

    @Transactional
    public void deleteCustomer(String id) throws MiException{
        try {
            Customer customer = customerRepository.findById(id).get();
            imageService.Delete(customer.getImage());
            customerRepository.delete(customer);
        } catch (Exception e) {
            throw new MiException("ERROR al borrar Usuario");
        }
    }

    //eric: falta actualizarlo para cambiar la imagen
    @Transactional
    public void modifyCustomer(Customer customer) throws MiException {
        
        validateData(customer.getName(), customer.getEmail(), customer.getLocation());
        
        try {
            customerRepository.save(customer);
        } catch (Exception e) {
            throw new MiException("ERROR al borrar Usuario");
        }
    }

    private void validateData(String name, String email, Locations location) throws MiException {
        if (name.isEmpty() ) {
            throw new MiException("NOMBRE Customer invalido o vacio");
        } else if (email.isEmpty()) {
            throw new MiException("EMAIL invalido o vacio");
        } else if (location == null) {
            throw new MiException("la LOCALIAD es invalido o vacio");
        }
    }
    
    public Customer getById(String id){
        return customerRepository.findById(id).get();
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.searchByEmail(email);

        if (customer != null) {

            List<GrantedAuthority> authorities = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + customer.getRole().toString());

            authorities.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("customersession", customer);

            return (UserDetails) new User(customer.getEmail(), customer.getPassword(), authorities);
        } else {
            return null;
        }
    }
}
