package org.cms.service.impl;

import lombok.RequiredArgsConstructor;
import org.cms.dto.AddressDto;
import org.cms.dto.CustomerDTO;
import org.cms.entity.*;
import org.cms.execption.ResourceNotFoundException;
import org.cms.repository.CityRepository;
import org.cms.repository.CountryRepository;
import org.cms.repository.CustomerRepository;
import org.cms.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    @Override
    public Customer createCustomer(CustomerDTO customerDTO) {

        Optional<Customer> existingCustomer = customerRepository.findByNic(customerDTO.getNic());

        Customer customerEntity = modelMapper.map(customerDTO, Customer.class);

        if (existingCustomer.isPresent()) {
            customerEntity.setId(existingCustomer.get().getId());
        }

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

        if (customerDTO.getAddresses() != null) {
            List<CustomerAddress> addressEntities = new ArrayList<>();

            for (AddressDto addressDTO : customerDTO.getAddresses()) {
                Country country = countryRepository.findByName(addressDTO.getCountry().getName())
                        .orElseThrow(() -> new ResourceNotFoundException("Country not found: " + addressDTO.getCountry().getName()));

                Optional<City> optionalCity = cityRepository.findByName(addressDTO.getCity().getName());
                City city;
                if (optionalCity.isPresent()) {
                    city = optionalCity.get();
                } else {
                    city = new City();
                    city.setName(addressDTO.getCity().getName());
                    city.setCountry(country);
                    city = cityRepository.save(city);
                }

                CustomerAddress addressEntity = modelMapper.map(addressDTO, CustomerAddress.class);
                addressEntity.setCity(city);
                addressEntity.setCustomer(customerEntity);
                addressEntities.add(addressEntity);
            }
            customerEntity.setAddresses(addressEntities);
        }
        if (customerDTO.getFamilyMembers() != null) {
            List<Customer> familyEntities = new ArrayList<>();

            for (CustomerDTO memberDTO : customerDTO.getFamilyMembers()) {
                Customer childCustomer = modelMapper.map(memberDTO, Customer.class);

                childCustomer.setParentCustomer(customerEntity);

                familyEntities.add(childCustomer);
            }
            customerEntity.setFamilyMembers(familyEntities);
        }
        return customerRepository.save(customerEntity);
    }

    @Override
    public Customer updateCustomer(String Nic, CustomerDTO customerDTO) {
        customerDTO.setNic(Nic);
        return createCustomer(customerDTO);
    }


    @Override
    public Page<CustomerDTO> getAllCustomers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customerPage = customerRepository.findAll(pageable);

        return customerPage.map(customer -> {
            CustomerDTO dto = modelMapper.map(customer, CustomerDTO.class);
            if (customer.getMobileNumbers() != null) {
                List<String> realMobileNumbers = customer.getMobileNumbers().stream()
                        .map(CustomerMobile::getMobileNumber)
                        .collect(Collectors.toList());
                dto.setMobileNumbers(realMobileNumbers);
            }
            return dto;
        });
    }
}
