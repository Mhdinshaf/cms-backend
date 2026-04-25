package org.cms.service;

import org.cms.dto.CustomerDTO;
import org.cms.entity.Customer;
import org.springframework.data.domain.Page;

public interface CustomerService {
    Customer createCustomer(CustomerDTO customerDTO);
    Customer updateCustomer(String nic, CustomerDTO customerDTO);
    Page<CustomerDTO> getAllCustomers(int page, int size);
}
