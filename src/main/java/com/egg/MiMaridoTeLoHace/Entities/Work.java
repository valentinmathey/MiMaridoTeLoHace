package com.egg.MiMaridoTeLoHace.Entities;

import javax.persistence.Entity;

@Entity
public class Work {
    
    private boolean allowProvider;
    private String description;
    private Provider provider;
    private Customer customer;
    
    public Work() {
    }

    public boolean isAllowProvider() {
        return allowProvider;
    }
    public void setAllowProvider(boolean allowProvider) {
        this.allowProvider = allowProvider;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Provider getProvider() {
        return provider;
    }
    public void setProvider(Provider provider) {
        this.provider = provider;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
