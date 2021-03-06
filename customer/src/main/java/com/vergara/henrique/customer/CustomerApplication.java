package com.vergara.henrique.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {
    "com.vergara.henrique.customer",
    "com.vergara.henrique.amqp"
})
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.vergara.henrique.clients")
public class CustomerApplication {

  public static void main(String[] args) {
    SpringApplication.run(CustomerApplication.class, args);
  }
}
