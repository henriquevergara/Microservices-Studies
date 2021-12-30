package com.vergara.henrique.customer;

import com.vergara.henrique.amqp.RabbitMQMessageProducer;
import com.vergara.henrique.clients.fraud.FraudCheckResponse;
import com.vergara.henrique.clients.fraud.FraudClient;
import com.vergara.henrique.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;
  private final FraudClient fraudClient;
  private final RabbitMQMessageProducer rabbitMQMessageProducer;

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
    FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

    if (fraudCheckResponse.isFraudster()) {
      throw new IllegalStateException("fraudster");
    }

    NotificationRequest notificationRequest = new NotificationRequest(
        customer.getId(),
        customer.getEmail(),
        String.format("Hi %s, welcome to Amigoscode...",
            customer.getFirstName())
    );
    rabbitMQMessageProducer
        .publish(notificationRequest, "internal.exchange", "internal.notification.routing-key");
  }
}
