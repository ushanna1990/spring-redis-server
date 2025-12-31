package com.redis.server.controller;

import com.redis.server.model.Customer;
import com.redis.server.model.CustomerRequest;
import com.redis.server.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/redis/customer")
public class CustomerController {

    @Autowired
    private CustomerService redisServerService;

    @PostMapping("/saveCustomer")
    public ResponseEntity<Customer> saveCustomer(@RequestBody CustomerRequest customerRequest) {
        return ResponseEntity.ok(redisServerService.saveCustomer(customerRequest));
    }

    @PutMapping("/updateCustomer")
    public ResponseEntity<Customer> updateCustomer(@RequestBody CustomerRequest customerRequest) {
        return ResponseEntity.ok(redisServerService.updateCustomer(customerRequest));
    }

    @GetMapping("/getCustomer")
    public ResponseEntity<Customer> getCustomer(@RequestParam String id) {
        return ResponseEntity.ok(redisServerService.getCustomer(id));
    }

    @GetMapping("/getAllCustomers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(redisServerService.getAllCustomers());
    }

    @DeleteMapping("/deleteCustomer")
    public ResponseEntity<String> deleteCustomer(@RequestParam String id) {
        return ResponseEntity.ok(redisServerService.deleteCustomer(id));
    }

    @DeleteMapping("/deleteAllCustomers")
    public ResponseEntity<String> deleteAllCustomers() {
        return ResponseEntity.ok(redisServerService.deleteAllCustomers());
    }
}
