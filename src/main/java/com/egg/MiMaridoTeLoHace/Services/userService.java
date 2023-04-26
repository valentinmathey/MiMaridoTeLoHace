package com.egg.MiMaridoTeLoHace.Services;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.egg.MiMaridoTeLoHace.Entities.Image;
import com.egg.MiMaridoTeLoHace.Entities.User;
import com.egg.MiMaridoTeLoHace.Enums.Roles;
import com.egg.MiMaridoTeLoHace.Exceptions.MiException;
import com.egg.MiMaridoTeLoHace.Repositories.ImageRepository;
import com.egg.MiMaridoTeLoHace.Repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImageRepository imageRepository;


    //---- CRUD USER ------ (Se usara para crear, modificar y boorrar Customers, y solo para crear Admins)
    @Transactional
    public void createUser(User user, Image image) throws MiException{
        
        try {
            
            user.setAlta(true);
            user.setImage(image.getId());
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

            //TODOS LOS USER NACEN POR DEFECTO COMO UN CUSTOMER, DE FORMA MANUAL DEBERIAMOS DAR PRIVILEGIOS DE ADMIN
            user.setRole(Roles.CUSTOMER);

            userRepository.save(user);

        } catch (Exception e) {
            throw new MiException("null");
        }
    }

    @Transactional
    public void modifyUSer() throws MiException{

        try {
            
        } catch (Exception e) {
            throw new MiException("null");
        }
    }

    @Transactional
    public void deleteUser() throws MiException{

        try {
            
        } catch (Exception e) {
            throw new MiException("null");
        }
    }
    
    //---- CRUD PROVIDER ------ (Se usara para crear, modificar y boorrar Customers, y solo para crear Admins)
    
    @Transactional
    public void createProvider() throws MiException{

        try {
            
        } catch (Exception e) {
            throw new MiException("null");
        }
    }

    @Transactional
    public void modifyProvider() throws MiException{

        try {
            
        } catch (Exception e) {
            throw new MiException("null");
        }
    }

    @Transactional
    public void deleteProvider() throws MiException{

        try {
            
        } catch (Exception e) {
            throw new MiException("null");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    }
    
    
    
    
    
    
    
    
    
    
    /*@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        User user = userRepository.searchByEmail(email);

        if (user != null) {

            List<GrantedAuthority> authorities = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + user.getRole().toString());

            authorities.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usersession", user);

            return (UserDetails) new User(user.getEmail(), user.getPassword(), authorities);

        } else {
            return null;
        }
    }*/
}
