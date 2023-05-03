package com.egg.MiMaridoTeLoHace.Services;

import com.egg.MiMaridoTeLoHace.Entities.Work;
import com.egg.MiMaridoTeLoHace.Entities.User;
import com.egg.MiMaridoTeLoHace.Enums.Roles;
import com.egg.MiMaridoTeLoHace.Exceptions.MiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.egg.MiMaridoTeLoHace.Repositories.WorkRepository;

@Service
public class WorkService {
    //pruebas con reviews

    @Autowired
    WorkRepository workRepository;

    @Autowired
    UserService userService;
    
    public void createWork(User customer, User provider, Work workSave) throws MiException {
        

    }
    
    public void createReview(String Id, Work workReview) throws MiException {


    }

    //tanto provider como user puede consultar sus review
    public void searchWork(User user){
        //eric: no se por que estan separados, pero puedo pensar que solo para que el customer pueda editar los comentarios?
        if (user.getRole().equals(Roles.PROVIDER)){
            workRepository.getWorkByUserProvider(user.getId());
        }else if (user.getRole().equals(Roles.CUSTOMER)){
            workRepository.getWorkByUserCustomer(user.getId());
        }

    }
}