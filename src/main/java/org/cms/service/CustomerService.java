package org.cms.service;

import org.cms.dto.CustomerDTO;
import org.cms.entity.Customer;

public interface CustomerService {
    Customer createCustomer(CustomerDTO customerDTO);

    Customer updateCustomer(String nic, CustomerDTO customerDTO);
}
