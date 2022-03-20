package com.example.carDealer.service;

import java.io.IOException;

public interface CustomerService {
    void seedCustomerData() throws IOException;

    Long findRandomCustomerId();
}
