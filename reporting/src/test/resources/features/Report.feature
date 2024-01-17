Feature: Report feature

  Scenario: Save a payment
    When a PAYMENT_TRANSFERRED event is received
    Then the payment should be saved

  Scenario: Get Customer report
    When a PAYMENT_TRANSFERRED event is received
    Then the payment should be saved

  Scenario: Get Customer payment
    When a CUSTOMER_REPORT_REQUESTED event is received
    Then the report should be returned to the customer
    And the report should contain a token and merchantID for each payment

  Scenario: Save a payment
    When a PAYMENT_TRANSFERRED event is received
    Then the payment should be saved