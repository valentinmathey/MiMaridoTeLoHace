package com.egg.MiMaridoTeLoHace.Services;

import com.egg.MiMaridoTeLoHace.Entities.Review;
import com.egg.MiMaridoTeLoHace.Entities.User;
import com.egg.MiMaridoTeLoHace.Enums.Roles;
import com.egg.MiMaridoTeLoHace.Exceptions.MiException;
import com.egg.MiMaridoTeLoHace.Repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    //pruebas con reviews

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserService userService;
    public void createAndAssignReview(User client, User provider, Review reviewSave) throws MiException {
        Review review = new Review();

        review.setRating(reviewSave.getRating());
        review.setMessage(reviewSave.getMessage());

        review.setUserCustomerId(client);
        review.setUserProviderId(provider);

        //comentado por dar error
//        userService.recalculateRating(provider.getId());
    }

    //tanto provider como user puede consultar sus review
    public void searchReview(User user){
        //eric: no se por que estan separados, pero puedo pensar que solo para que el customer pueda editar los comentarios?
        if (user.getRole().equals(Roles.PROVIDER)){
            reviewRepository.getReviewByUserProvider(user.getId());
        }else if (user.getRole().equals(Roles.CUSTOMER)){
            reviewRepository.getReviewByUserCustomer(user.getId());
        }

    }
}