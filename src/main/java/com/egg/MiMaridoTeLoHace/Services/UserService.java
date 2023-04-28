package com.egg.MiMaridoTeLoHace.Services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    // ---- CRUD USER ------ (Se usara para crear, modificar y boorrar Customers, y
    // solo para crear Admins)
    @Transactional
    public void createUser(User user) throws MiException {

        try {

            Image image = null;

            if (user.getProfession() == null) {
                user.setRole(Roles.CUSTOMER);
                image = imageService.GetByName("customer-avatar.png");
            } else {
                user.setRole(Roles.PROVIDER);
                image = imageService.GetByName("provider-avatar.png");
            }

            imageService.Save(image);
            user.setImage(image.getId());
            user.setAlta(true);
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

            userRepository.save(user);

        } catch (Exception e) {
            throw new MiException("Error al crear USER!");
        }
    }

    // Este metodo se puede modificar para actualizar imagen, por ahora queda asi
    @Transactional
    public void modifyUser(String id, User user) throws MiException {

        try {

            Optional<User> userCheck = userRepository.findById(id);

            if (userCheck.isPresent()) {

                User newUser = userCheck.get();
                newUser.setAlta(true);
                newUser.setName(user.getName());
                newUser.setLastname(user.getLastname());
                newUser.setEmail(user.getEmail());
                newUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

                if (user.getRole().name().equals("PROVIDER")) {
                    newUser.setDescription(user.getDescription());
                    newUser.setRating(Math.random() * 5 + 1); // uso hasta tener reviews
                    newUser.setProfession(user.getProfession());
                    newUser.setSubscription(new Date(System.currentTimeMillis()));
                }

                userRepository.save(newUser);
            }

        } catch (Exception e) {
            throw new MiException("ERROR al modificar USUARIO " + user.getName() + " " + user.getLastname());
        }
    }

    @Transactional
    public void deleteUser(String id) throws MiException {

        try {

            Optional<User> userCheck = userRepository.findById(id);

            if (userCheck.isPresent()) {
                userRepository.delete(userRepository.getById(id));
                ;
            }

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
    public Optional<User> searchByProfession(String professions) throws MiException {
        for (Professions pr : Professions.values()) {
            if (pr.name().equals(professions)) {
                return userRepository.searchByProfession(pr);
            }
        }
        return null;
    }

    @Transactional
    public boolean validateEmail(User user) throws MiException {

        boolean validator=false;

        if (userRepository.searchByEmail(user.getEmail())!=null) {
            validator=true;
        }
        // si el validador se vuelve verdadero, es porque hay coincidencia de emails.
        return validator;

    }
    // ---- CRUD PROVIDER ------ (Se usara para crear, modificar y boorrar
    // Customers, y solo para crear Admins)
    //
    // @Transactional
    // public void createProvider(User provider, Image image) throws MiException{
    //
    // try {
    //
    // provider.setAlta(true);
    // provider.setImage(image.getId());
    // provider.setPassword(new
    // BCryptPasswordEncoder().encode(provider.getPassword()));
    //
    // provider.setRole(Roles.PROVIDER);
    // provider.setSubscription(new Date(System.currentTimeMillis()));
    //
    // //La descipcion, profesion y rating vienen dentro del objeto que llega por
    // parametros
    // userRepository.save(provider);
    //
    // } catch (Exception e) {
    // throw new MiException("Error al crear USER!");
    // }
    // }
    //
    // @Transactional
    // public void modifyProvider(String id, User provider) throws MiException{
    //
    // try {
    //
    // Optional<User> providerCheck = userRepository.findById(id);
    //
    // if (providerCheck.isPresent()) {
    //
    // User newProvider = providerCheck.get();
    // newProvider.setAlta(true);
    // newProvider.setName(provider.getName());
    // newProvider.setLastname(provider.getLastname());
    // newProvider.setDescription(provider.getDescription());
    // newProvider.setProfession(provider.getProfession());
    // newProvider.setEmail(provider.getEmail());
    // newProvider.setPassword(new
    // BCryptPasswordEncoder().encode(provider.getPassword()));
    //
    // userRepository.save(newProvider);
    // }
    //
    // } catch (Exception e) {
    // throw new MiException("ERROR al modificar PROVEEDOR "+provider.getName()+"
    // "+provider.getLastname());
    // }
    // }

    //
    // @Transactional
    // public void deleteProvider(String id, User provider) throws MiException{
    //
    // try {
    //
    // Optional<User> providerCheck = userRepository.findById(id);
    //
    // if (providerCheck.isPresent()) {
    // userRepository.delete(userRepository.getById(id));
    // }
    //
    // } catch (Exception e) {
    // throw new MiException("ERROR al borrar PROVIDER!");
    // }
    // }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.searchByEmail(email);

        if (user != null) {

            List<GrantedAuthority> authorities = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + user.getRole().toString());

            authorities.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usersession", user);

            return (UserDetails) new org.springframework.security.core.userdetails.User(email, email, authorities);

        } else {
            return null;
        }
    }
}
