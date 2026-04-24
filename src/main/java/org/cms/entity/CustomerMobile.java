package org.cms.entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class CustomerMobile {

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
