package com.egg.MiMaridoTeLoHace.Services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.egg.MiMaridoTeLoHace.Enums.Professions;
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

    @Autowired
    ImageService imageService;

    // ---- Service USER ------ (Se usara para crear, modificar y borrar Customers y Providers)
    @Transactional
    public void createUser(User user) throws MiException {

        try {

            Image image = null;

            if (user.getProfession() == null) {
                user.setRole(Roles.CUSTOMER);
                image = imageService.GetByName("customer-avatar.png");
            } else {
                user.setRole(Roles.PROVIDER);
                //agregado el raiting temporal
                user.setRating((int) Math.round(Math.random() * 5)); // uso hasta tener reviews
                image = imageService.GetByName("provider-avatar.png");
            }

            imageService.Save(image);
            user.setImage(image.getId());
            user.setAlta(true);
            //eric: agregada Subscription date de forma global
            user.setSubscription(new Date(System.currentTimeMillis()));
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

            userRepository.save(user);


        } catch (Exception e) {
            throw new MiException("Error al crear USER!");
        }
    }

    @Transactional
    public User modifyUser(String id, User user, Image image) throws MiException {
        //eric: metodo reecho
        try {
            User originalUser = userRepository.findById(id).get();

            if (image != null){
                imageService.Delete(originalUser.getImage());
                imageService.Save(image);
                originalUser.setImage(image.getId());
            }
            if (originalUser.getRole().equals(Roles.PROVIDER)) {
                originalUser.setDescription(user.getDescription());
                originalUser.setProfession(user.getProfession());
            }
            originalUser.setName(user.getName());
            originalUser.setLastname(user.getLastname());

            if(user.getPassword() != null){
                originalUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            }

            originalUser.setPhone(user.getPhone());

            userRepository.save(originalUser);
            return originalUser;

        } catch (Exception e) {
            throw new MiException("ERROR al modificar USUARIO " + user.getName() + " " + user.getLastname() + ". \n error: " + e.getMessage());
        }
    }

    @Transactional
    public void deleteUser(String id) throws MiException {
        //eric: metodo reecho
        try {
            User originalUser = userRepository.findById(id).get();
            originalUser.setAlta(false);
            originalUser.setUnsubscription(new Date(System.currentTimeMillis()));
        } catch (Exception e) {
            throw new MiException("ERROR al borrar USUARIO!");
        }
    }
    public User getById(String id) throws MiException {
        try {
            return userRepository.findById(id).get();
        } catch (Exception e) {
            throw new MiException("Usuario no encontrado");
        }
    }
    public User getByEmail(String email) throws MiException {
        try {
            return userRepository.searchByEmail(email);
        } catch (Exception e) {
            throw new MiException("Usuario no encontrado");
        }
    }

    public List<User> userList() throws MiException {

        List<User> usersList = new ArrayList<>();

        usersList = userRepository.findAll();

        return usersList;
    }

    @Transactional
    public List<User> providerList() throws MiException {

        List<User> providersList = new ArrayList<>();
        providersList = userRepository.findByRole(Roles.PROVIDER);
        return providersList;
    }

    @Transactional
    public List<User> searchByProfessionAlta(String professions) throws MiException {
        for (Professions pr : Professions.values()) {
            if (pr.name().equals(professions)) {
                return userRepository.searchByProfessionAlta(pr);
            }
        }
        return null;
    }

    @Transactional
    public List<User> providersAndCustomers() throws MiException {

        List<User> onlyUsers = new ArrayList<>();

        try {

            for (User user : userRepository.findAll()) {
                if (!user.getRole().toString().equals("ADMIN")) {
                    onlyUsers.add(user);
                }
            }

        } catch (Exception e) {
            throw new MiException("ERROR AL GUARDAR CUSTOMER Y PROVIDERS (ALGUN ROL VACIO EN DB?)");
        }

        return onlyUsers;
    }

    @Transactional
    public boolean validateEmail(User user) throws MiException {

        boolean validator = false;

        if (userRepository.searchByEmail(user.getEmail()) != null) {
            validator = true;
        }
        // si el validador se vuelve verdadero, es porque hay coincidencia de emails.
        return validator;

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.searchByEmail(email);

        if (user != null) {

            List<GrantedAuthority> authorities = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + user.getRole().toString());

            authorities.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("userSession", user);

            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                    authorities);

        } else {
            return null;
        }
    }

    //prueba reviews
//    public void recalculateRating(String id) throws MiException {
//        try {
//            User user = getById(id);
//            int newRating = 0;
//            for (int i = 0; i <= user.getReviewRecibidas().size(); i++){
//                newRating += user.getReviewRecibidas().get(i).getRating();
//            }
//            newRating = Math.round( (float) newRating / user.getReviewRecibidas().size());
//
//            user.setRating(newRating);
//        } catch (MiException e) {
//            throw new MiException("MSG: no se pudo calcular el rating. \n ERROR: " + e.getMessage());
//        }
//    }
}
