Feature: Report feature

  Scenario: Generate a report for a customer
    Given A "PAYMENT_TRANSFERRED" event
    When the customer requests a report
    Then the customer should see a report with the following transaction details


  Scenario: Generate a report for a merchant
    Given A "PAYMENT_TRANSFERRED" event
    When the merchant requests a report
    Then the merchant should see a report with the following transaction details