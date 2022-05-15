package com.epam.cleandesign.domain;

public interface CustomerRepository {
    Customer get(int customerId);
}
