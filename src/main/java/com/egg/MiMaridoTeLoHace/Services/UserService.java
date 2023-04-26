package com.egg.MiMaridoTeLoHace.Services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
            throw new MiException("Error al crear USER!");
        }
    }


    //Este metodo se puede modificar para actualizar imagen, por ahora queda asi
    @Transactional
    public void modifyUser(String id, User user) throws MiException{

        try {

            Optional<User> userCheck = userRepository.findById(id);

            if (userCheck.isPresent()) {
                
                User newUser = userCheck.get();
                newUser.setAlta(true);
                newUser.setName(user.getName());
                newUser.setLastname(user.getLastname());
                newUser.setEmail(user.getEmail());
                newUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

                userRepository.save(newUser);
            }


        } catch (Exception e) {
            throw new MiException("ERROR al modificar USUARIO "+user.getName()+" "+user.getLastname());
        }
    }

    @Transactional//eric: añadido valores recibidos
    public void deleteUser(String id) throws MiException{

        try {

            Optional<User> userCheck = userRepository.findById(id);

            if (userCheck.isPresent()) {
                userRepository.delete(userRepository.getById(id));;
            }

        } catch (Exception e) {
            throw new MiException("ERROR al borrar USUARIO!");
        }
    }

    //eric: añadido los getBy
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

    public List <User> userList() throws MiException{
        
        List <User> usersList = new ArrayList<>();
        
        usersList = userRepository.findAll();

        return usersList;
    }

    //---- CRUD PROVIDER ------ (Se usara para crear, modificar y boorrar Customers, y solo para crear Admins)

    @Transactional
    public void createProvider(User provider, Image image) throws MiException{

        try {

            provider.setAlta(true);
            provider.setImage(image.getId());
            provider.setPassword(new BCryptPasswordEncoder().encode(provider.getPassword()));

            provider.setRole(Roles.PROVIDER);
            provider.setSubscription(new Date(System.currentTimeMillis()));
            
            //La descipcion, profesion y rating vienen dentro del objeto que llega por parametros
            userRepository.save(provider);

        } catch (Exception e) {
            throw new MiException("Error al crear USER!");
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
