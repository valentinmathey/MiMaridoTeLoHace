package com.egg.MiMaridoTeLoHace.Services;

import com.egg.MiMaridoTeLoHace.Entities.Work;
import com.egg.MiMaridoTeLoHace.Enums.WorkStatus;
import com.egg.MiMaridoTeLoHace.Exceptions.MiException;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.egg.MiMaridoTeLoHace.Repositories.WorkRepository;

@Service
public class WorkService {
    // pruebas con reviews
    @Autowired
    WorkRepository workRepository;

    @Autowired
    UserService userService;

    @Transactional
    public void createWork(Work work) throws MiException {

        try {

            work.setWorkStatus(WorkStatus.REQUIRED);
            workRepository.save(work);

        } catch (Exception e) {
            throw new MiException("ERROR al generar solicitud de trabajo");
        }
    }

    //--------------------------------- EVALUAR SI LO VAMOS A USAR DESDE ADMIN ---------------------------------
    // @Transactional
    // public void editReview(Work work) {
    //     Work original = getById(work.getId());

    //     if (original.getWorkStatus().name().equals("REVIEWD")) {
    //         original.setRatingWork(work.getRatingWork());
    //         original.setReview(work.getReview());
    //     } else {
    //         if (work.getWorkStatus().name().equals("REVIEWD")) {
    //             original.setWorkStatus(WorkStatus.DONE);
    //         } else if (work.getWorkStatus().name().equals("DONE")) {
    //             original.setWorkStatus(WorkStatus.ACCEPTED);
    //         }
    //     }
    //     workRepository.save(original);
    // }

    @Transactional
    public void delete(String id) {
        workRepository.delete(getById(id));
    }

    public Work getById(String id) {
        return workRepository.findById(id).get();
    }

    @Transactional
    public void changeWorkStatus(String id, String wStat) throws MiException{
        
        try {
            
            Optional<Work> work = workRepository.findById(id);
            if (work.isPresent() && wStat.equals("REVERT") || wStat.equals("ACCEPTED") || wStat.equals("DONE")) {
                Work newWorkStatus = work.get();
                newWorkStatus.setWorkStatus(WorkStatus.valueOf(wStat)); 
            }

        } catch (Exception e) {
            throw new MiException("ERROR AL MODIFICAR WORK STATUS");
        }

    }

}