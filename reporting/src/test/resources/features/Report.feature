Feature: Report feature

  Scenario: Save a payment
    When a PAYMENT_TRANSFERRED event is received
    Then the payment should be saved