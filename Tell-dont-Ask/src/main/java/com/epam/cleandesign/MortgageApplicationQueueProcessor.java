package com.epam.cleandesign;


import com.epam.cleandesign.domain.Customer;
import com.epam.cleandesign.domain.CustomerRepository;
import com.epam.cleandesign.exceptions.NotEligibleForMortgageException;
import com.epam.cleandesign.exceptions.WrongDataException;


public class MortgageApplicationQueueProcessor {

    private final String MESSAGE_INVALID_CUSTOMER = "Customer not found!";

    private final CustomerRepository customerRepository;

    public MortgageApplicationQueueProcessor(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void processRequest(int customerId, Double amountRequested) {
        Customer customer = customerRepository.get(customerId);

        if (customer == null)
            throw new WrongDataException(MESSAGE_INVALID_CUSTOMER);

        customer.updateBalance(amountRequested);
    }

}
