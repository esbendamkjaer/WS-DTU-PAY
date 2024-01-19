Feature: Report feature

  # Author: Muhamad Hussein Nadali (s233479)
  Scenario: Generate a report for a customer
    Given a customer named "Alice"
    And a merchant named "Bob"
    And the customer has a bank account with balance 500.0
    And the merchant has a bank account with balance 0.0
    And the customer is registered with DTU Pay
    And the merchant is registered with DTU Pay
    When the customer gets 1 tokens
    And the merchant requests a payment of 100 kr
    When the customer requests a report
    Then the customer should see a report with the following transaction details

  # Author: Alexander Matzen (s233475)
  Scenario: Generate a report for a merchant
    Given a customer named "Alice"
    And a merchant named "Bob"
    And the customer has a bank account with balance 500.0
    And the merchant has a bank account with balance 0.0
    And the customer is registered with DTU Pay
    And the merchant is registered with DTU Pay
    When the customer gets 1 tokens
    And the merchant requests a payment of 100 kr
    When the merchant requests a report
    Then the merchant should see a report with the following transaction details

    # Author: Dilara Eda Celepli (s184262)
    Scenario: Generate a report for a manager
    When the manager requests a report
    Then the manager should see a report with the following transaction details


