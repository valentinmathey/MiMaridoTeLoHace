/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

            switch (profession) {
                case "GASISTA":
                    if (location.equals("BARRIO_1")) {
                        return PR.searchByLocationAndProfession(Locations.BARRIO_1, Professions.GASISTA);
                    }
                    if (location.equals("BARRIO_2")) {
                        return PR.searchByLocationAndProfession(Locations.BARRIO_2, Professions.GASISTA);
                    }
                    if (location.equals("BARRIO_3")) {
                        return PR.searchByLocationAndProfession(Locations.BARRIO_3, Professions.GASISTA);
                    }

                case "ELECTRICISTA":
                    if (location.equals("BARRIO_1")) {
                        return PR.searchByLocationAndProfession(Locations.BARRIO_1, Professions.GASISTA);
                    }
                    if (location.equals("BARRIO_2")) {
                        return PR.searchByLocationAndProfession(Locations.BARRIO_2, Professions.GASISTA);
                    }
                    if (location.equals("BARRIO_3")) {
                        return PR.searchByLocationAndProfession(Locations.BARRIO_3, Professions.GASISTA);
                    }

                case "PLOMERO":
                    if (location.equals("BARRIO_1")) {
                        return PR.searchByLocationAndProfession(Locations.BARRIO_1, Professions.GASISTA);
                    }
                    if (location.equals("BARRIO_2")) {
                        return PR.searchByLocationAndProfession(Locations.BARRIO_2, Professions.GASISTA);
                    }
                    if (location.equals("BARRIO_3")) {
                        return PR.searchByLocationAndProfession(Locations.BARRIO_3, Professions.GASISTA);
                    }

                case "LIMPIEZA":
                    if (location.equals("BARRIO_1")) {
                        return PR.searchByLocationAndProfession(Locations.BARRIO_1, Professions.GASISTA);
                    }
                    if (location.equals("BARRIO_2")) {
                        return PR.searchByLocationAndProfession(Locations.BARRIO_2, Professions.GASISTA);
                    }
                    if (location.equals("BARRIO_3")) {
                        return PR.searchByLocationAndProfession(Locations.BARRIO_3, Professions.GASISTA);
                    }

                case "CERRAJERO":
                    if (location.equals("BARRIO_1")) {
                        return PR.searchByLocationAndProfession(Locations.BARRIO_1, Professions.GASISTA);
                    }
                    if (location.equals("BARRIO_2")) {
                        return PR.searchByLocationAndProfession(Locations.BARRIO_2, Professions.GASISTA);
                    }
                    if (location.equals("BARRIO_3")) {
                        return PR.searchByLocationAndProfession(Locations.BARRIO_3, Professions.GASISTA);
                    }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Provider> searchLocation(String search) throws Exception {
        try {
            switch (search){
                case "BARRIO_1":
                    return PR.searchByLocation(Locations.BARRIO_1);
                case "BARRIO_2":
                    return PR.searchByLocation(Locations.BARRIO_1);
                case "BARRIO_3":
                    return PR.searchByLocation(Locations.BARRIO_1);
                default:
                    return null;
            }

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public List<Provider> getAll() throws Exception {
        try {
            return PR.findAll();
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void save(Provider p){
        PR.save(p);
    }
}
