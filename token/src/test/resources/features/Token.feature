Feature:
  Scenario: Payment initiated
    Given a valid token
    When a PaymentInitiatedEvent is received
    Then a corresponding TokenValidatedEvent is sent

  Scenario: Payment initiated with invalid token
    Given an invalid token
    When a PaymentInitiatedEvent is received
    Then a corresponding TokenInvalidatedEvent is sent
    