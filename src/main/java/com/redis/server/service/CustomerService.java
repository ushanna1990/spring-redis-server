package com.redis.server.service;

import com.redis.server.model.Customer;
import com.redis.server.model.CustomerRequest;
import com.redis.server.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    //@CachePut always executes the method and call the DB first, then updates redis cache with the returned result.
    @CachePut(value = "customer", key = "#customerRequest.id")
    public Customer saveCustomer(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setId(customerRequest.getId());
        customer.setName(customerRequest.getName());
        customer.setEmail(customerRequest.getEmail());
        return customerRepository.save(customer);
    }

    //@CachePut always executes the method and call the DB first, then updates the redis cache.
    @CachePut(value = "customer", key = "#result.id")
    public Customer updateCustomer(CustomerRequest customerRequest) {
        Customer customer = customerRepository.findById(customerRequest.getId()).orElse(new Customer());
        customer.setId(customerRequest.getId());
        customer.setName(customerRequest.getName());
        customer.setEmail(customerRequest.getEmail());
        return customerRepository.save(customer);
    }

    //@Cacheable first checks the cache, if the data is present in cache then it returns from cache without executing the method.
    @Cacheable(value = "customer", key = "#id")
    public Customer getCustomer(String id) {
        return customerRepository.findById(id).orElse(new Customer());
    }

    //@Cacheable first checks the cache, if the data is not present in cache then it executes the method and calls the DB, then stores the returned result in redis cache.
    @Cacheable(value = "customer", key = "'all'")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    //@CacheEvict always executes the method and call the DB first, then removes the redis cache by default.
    @CacheEvict(value = "customer", key = "#id")
    public String deleteCustomer(String id) {
        customerRepository.deleteById(id);
        return "Customer deleted successfully";
    }

    //@CacheEvict if beforeInvocation = true, removes the redis cache first, then executes the method and calls the DB.
    @CacheEvict(value = "customer", key = "'all'", allEntries = true, beforeInvocation = true)
    public String deleteAllCustomers() {
        System.out.println("Deleting all customers from database");
        customerRepository.deleteAll();
        return "All customers deleted successfully";
    }
}
