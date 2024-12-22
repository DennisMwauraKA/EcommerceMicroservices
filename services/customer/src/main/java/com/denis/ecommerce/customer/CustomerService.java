package com.denis.ecommerce.customer;

import com.denis.ecommerce.exception.CustomerNotFoundException;
import jakarta.validation.Valid;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepo customerRepo, CustomerMapper customerMapper) {
        this.customerRepo = customerRepo;
        this.customerMapper = customerMapper;
    }

    // create customer
    public String createCustomer(CustomerRequest customerRequest) {
        var customer = customerRepo.save(customerMapper.toCustomer(customerRequest));
        return customer.toString();

    }


    // update a customer
    public void updateCustomer(@Valid CustomerRequest customerRequest) {
        var customer = customerRepo.findById(customerRequest.id()).orElseThrow(() -> new CustomerNotFoundException(String.format("Customer with id %s not found", customerRequest.id())));
        mergeCustomer(customer, customerRequest);
        customerRepo.save(customer);

    }

    private void mergeCustomer(Customer customer, @Valid CustomerRequest customerRequest) {
        if (StringUtils.isNotBlank(customerRequest.firstName())) {
            customer.setFirstName(customerRequest.firstName());
        }
        if (StringUtils.isNotBlank(customerRequest.lastName())) {
            customer.setLastName(customerRequest.lastName());
        }
        if (StringUtils.isNotBlank(customerRequest.email())) {
            customer.setEmail(customerRequest.email());
        }
        if (customerRequest.address() != null) {
            customer.setAddress(customerRequest.address());
        }
    }
//find all customers

    public List<CustomerResponse> findAllCustomers() {

        return customerRepo.findAll().stream().map(customerMapper::fromCustomer).collect(Collectors.toList());

    }

    // find if  a customer exists with the specific id
    public Boolean existsById(String customerId) {

        return customerRepo.findById(customerId).isPresent();
    }


    // find a customer with the said ID
    public CustomerResponse findCustomerById(String customerId) {
        return customerRepo.findById(customerId).map(customerMapper::fromCustomer).orElseThrow(() -> new CustomerNotFoundException(String.format("Customer with id %s not found", customerId)));
    }


    // delete a customer with the associated ID
    public String deleteById(String customerId) {

        customerRepo.deleteById(customerId);
        return customerId;
    }
}
