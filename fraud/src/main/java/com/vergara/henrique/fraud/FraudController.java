package com.vergara.henrique.fraud;

import com.vergara.henrique.clients.fraud.FraudCheckResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/fraud-check")
public class FraudController {

  @Autowired
  private FraudCheckService fraudCheckService;

  @GetMapping("{customerId}")
  public FraudCheckResponse isFraudster(@PathVariable Integer customerId) {
    log.info("## Received request to check fraud.");
    boolean isFraudulentCustomer = this.fraudCheckService.isFraudulentCustomer(customerId);

    return new FraudCheckResponse(isFraudulentCustomer);
  }
}
