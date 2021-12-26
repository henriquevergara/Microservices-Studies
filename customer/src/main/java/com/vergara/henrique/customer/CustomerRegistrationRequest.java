package com.vergara.henrique.customer;

public record CustomerRegistrationRequest(
    String firstName,
    String lastName,
    String email) {
}
