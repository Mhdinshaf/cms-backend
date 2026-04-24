package org.cms.service.impl;

import lombok.RequiredArgsConstructor;
import org.cms.dto.CustomerDTO;
import org.cms.entity.Customer;
import org.cms.repository.CustomerRepository;
import org.cms.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    public Customer createCustomer(CustomerDTO customerDTO) {
        Customer customerEntity = modelMapper.map(customerDTO, Customer.class);
        return customerRepository.save(customerEntity);
    }
}
