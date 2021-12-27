package com.vergara.henrique.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerService {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private RestTemplate restTemplate;

  public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
    Customer customer = Customer.builder()
        .firstName(customerRegistrationRequest.firstName())
        .lastName(customerRegistrationRequest.lastName())
        .email(customerRegistrationRequest.email())
        .build();

    // todo: check if email is valid
    // todo: check if email is not taken
    this.customerRepository.saveAndFlush(customer);
    // todo: check if fraudster
    FraudCheckResponse fraudCheckResponse = restTemplate
        .getForObject("http://FRAUD/api/v1/fraud-check/{customerId}",
            FraudCheckResponse.class,
            customer.getId()
        );

    if (fraudCheckResponse.isFraudster()) {
      throw new IllegalStateException("fraudster");
    }
    // todo: send notification
  }
}
