package org.cms.service.impl;

import lombok.RequiredArgsConstructor;
import org.cms.dto.CustomerDTO;
import org.cms.entity.Customer;
import org.cms.entity.CustomerMobile;
import org.cms.repository.CustomerRepository;
import org.cms.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    public Customer createCustomer(CustomerDTO customerDTO) {
        Customer customerEntity = modelMapper.map(customerDTO, Customer.class);

        if (customerDTO.getMobileNumbers() != null) {
            List<CustomerMobile> mobileList = new ArrayList<>();

            for (String number : customerDTO.getMobileNumbers()) {
                CustomerMobile mobile = new CustomerMobile();
                mobile.setMobileNumber(number);
                mobile.setCustomer(customerEntity);

                mobileList.add(mobile);
            }
            customerEntity.setMobileNumbers(mobileList);
        }

        return customerRepository.save(customerEntity);
    }
}
