package com.egg.MiMaridoTeLoHace.Services;

import com.egg.MiMaridoTeLoHace.Entities.Customer;
import com.egg.MiMaridoTeLoHace.Enums.Locations;
import com.egg.MiMaridoTeLoHace.Exceptions.MiException;
import com.egg.MiMaridoTeLoHace.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Transactional
    public void createCustomer(Customer customer) throws MiException {
        validateData(customer.getName(), customer.getEmail(), customer.getLocation());
        customerRepository.save(customer);
    }

    @Transactional
    public void deleteCustomer(String id){
        Customer customer = customerRepository.findById(id).get();
        customerRepository.delete(customer);
    }

    @Transactional
    public void modifyCustomer(Customer customer) throws MiException {
        validateData(customer.getName(), customer.getEmail(), customer.getLocation());
        customerRepository.save(customer);
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
    
    public Customer searchById(String id){
        return customerRepository.findById(id).get();
    }
}
