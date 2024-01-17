Feature:
  Scenario: Payment requested
    Given a valid token
    When a PaymentRequestedEvent is received
    Then a corresponding TokenValidatedEvent is sent

  Scenario: Payment requested with invalid token
    Given an invalid token
    When a PaymentRequestedEvent is received
    Then a corresponding TokenInvalidatedEvent is sent