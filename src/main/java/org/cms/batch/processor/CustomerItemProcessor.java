package org.cms.batch.processor;
import org.cms.dto.CustomerExcelDto;
import org.cms.entity.Customer;
import org.cms.entity.CustomerAddress;
import org.cms.entity.CustomerMobile;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerItemProcessor implements ItemProcessor<CustomerExcelDto, Customer> {

    @Override
    public Customer process(CustomerExcelDto dto) throws Exception {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setNic(dto.getNic());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        customer.setDob(LocalDate.parse(dto.getDob(), formatter));


        List<CustomerMobile> mobileNumbersList = new ArrayList<>();


        if (dto.getMobile1() != null && !dto.getMobile1().trim().isEmpty()) {
            CustomerMobile m1 = new CustomerMobile();
            m1.setMobileNumber(dto.getMobile1());
            m1.setCustomer(customer);
            mobileNumbersList.add(m1);
        }


        if (dto.getMobile2() != null && !dto.getMobile2().trim().isEmpty()) {
            CustomerMobile m2 = new CustomerMobile();
            m2.setMobileNumber(dto.getMobile2());
            m2.setCustomer(customer);
            mobileNumbersList.add(m2);
        }

        customer.setMobileNumbers(mobileNumbersList);


        List<CustomerAddress> addressesList = new ArrayList<>();

        if (dto.getAddress1City() != null && !dto.getAddress1City().trim().isEmpty()) {
            CustomerAddress a1 = new CustomerAddress();
            a1.setAddressLine1(dto.getAddress1City());
            a1.setCustomer(customer);
            addressesList.add(a1);
        }

        if (dto.getAddress2City() != null && !dto.getAddress2City().trim().isEmpty()) {
            CustomerAddress a2 = new CustomerAddress();
            a2.setAddressLine1(dto.getAddress2City());
            a2.setCustomer(customer);
            addressesList.add(a2);
        }

        customer.setAddresses(addressesList);


        return customer;
    }
}
