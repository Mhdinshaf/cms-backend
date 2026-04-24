package org.cms.controller;

import lombok.RequiredArgsConstructor;
import org.cms.dto.CustomerDTO;
import org.cms.entity.Customer;
import org.cms.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/Add")
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerDTO customerDTO) {

        Customer savedCustomer= customerService.createCustomer(customerDTO);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }
}
