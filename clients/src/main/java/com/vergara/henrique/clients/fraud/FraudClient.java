package com.vergara.henrique.clients.fraud;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("FRAUD")
public interface FraudClient {

  @LoadBalanced
  @GetMapping(path = "api/v1/fraud-check/{customerId}")
  FraudCheckResponse isFraudster(@PathVariable("customerId") Integer customerId);
}
