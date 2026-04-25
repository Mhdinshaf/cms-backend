package org.cms.batch.writer;

import org.cms.entity.Customer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Component
public class CustomerBatchWriter implements ItemWriter<Customer> {

    private final JdbcTemplate jdbcTemplate;

    public CustomerBatchWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(List<? extends Customer> customers) throws Exception {


        for (Customer customer : customers) {


            String customerSql = "INSERT INTO customer (name, nic, dob) VALUES (?, ?, ?)";


            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(customerSql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, customer.getName());
                ps.setString(2, customer.getNic());
                ps.setObject(3, customer.getDob());
                return ps;
            }, keyHolder);


            Number newId = keyHolder.getKey();

            if (newId != null) {
                long customerId = newId.longValue();


                if (customer.getMobileNumbers() != null && !customer.getMobileNumbers().isEmpty()) {
                    String mobileSql = "INSERT INTO customer_mobile (mobile_number, customer_id) VALUES (?, ?)";


                    jdbcTemplate.batchUpdate(mobileSql, customer.getMobileNumbers(), customer.getMobileNumbers().size(),
                            (ps, mobile) -> {
                                ps.setString(1, mobile.getMobileNumber());
                                ps.setLong(2, customerId);
                            });
                }


                if (customer.getAddresses() != null && !customer.getAddresses().isEmpty()) {
                    String addressSql = "INSERT INTO customer_address (address_line1, customer_id) VALUES (?, ?)";

                    jdbcTemplate.batchUpdate(addressSql, customer.getAddresses(), customer.getAddresses().size(),
                            (ps, address) -> {
                                ps.setString(1, address.getAddressLine1());
                                ps.setLong(2, customerId);
                            });
                }
            }
        }
    }
}