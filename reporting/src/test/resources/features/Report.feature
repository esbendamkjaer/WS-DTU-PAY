Feature: Report feature
#@author Muhamad Hussein Nadali (s233479)
  Scenario: Save a payment
    Given a Customer with id "8b3c9b82-45ca-4c4b-b946-a7055be9cf7a"
    And a Merchant with id "8b4c9b82-45ca-4c4b-b946-a7055be9cf7a"
    And a Payment with 300 kr
    When a PAYMENT_TRANSFERRED event is received
    Then the payment should be saved
#@author Clair Norah Mutebi (s184187)
  Scenario: Get Customer report
    Given a Customer with id "8b3c9b82-45ca-4c4b-b946-a7055be9cf7a"
    And a Merchant with id "8b4c9b82-45ca-4c4b-b946-a7055be9cf7a"
    And a Payment with 300 kr
    And a PAYMENT_TRANSFERRED event is received
    When a CUSTOMER_REPORT_REQUESTED event is received
    Then a report should be generated with all payments for the customer
    And a REPORT_GENERATED event should be published

#@author Dilara Eda Celepli (s184262)
  Scenario: Get Merchant report
    Given a Customer with id "8b3c9b82-45ca-4c4b-b946-a7055be9cf7a"
    And a Merchant with id "8b4c9b82-45ca-4c4b-b946-a7055be9cf7a"
    And a Payment with 300 kr
    And a PAYMENT_TRANSFERRED event is received
    When a MERCHANT_REPORT_REQUESTED event is received
    Then a report should be generated with all payments to the merchant
    And a REPORT_GENERATED event should be published


#@author Fuad Hassan Jama (s233468)
  Scenario: Get Manager report
    Given a Customer with id "8b3c9b82-45ca-4c4b-b946-a7055be9cf7a"
    And a Merchant with id "8b4c9b82-45ca-4c4b-b946-a7055be9cf7a"
    And a Payment with 300 kr
    And a PAYMENT_TRANSFERRED event is received
    When a MANAGER_REPORT_REQUESTED event is received
    Then a report should be generated with all payments
    And a REPORT_GENERATED event should be published



