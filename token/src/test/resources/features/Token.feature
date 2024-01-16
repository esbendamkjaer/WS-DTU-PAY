Feature:
  Scenario:
    Given a token
    When the PaymentRequestedEvent is received
    Then the TokenValidatedEvent is sent