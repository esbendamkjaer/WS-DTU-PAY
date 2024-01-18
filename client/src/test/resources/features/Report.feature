Feature: Report feature

  Scenario: Generate a report for a customer
    Given a customer named "Alice"
    And a merchant named "Bob"
    And the customer has a bank account with balance 500.0
    And the merchant has a bank account with balance 0.0
    When the customer gets 1 tokens
    And the merchant requests a payment of 100 kr
    When the customer requests a report
    Then the customer should see a report with the following transaction details


  Scenario: Generate a report for a merchant
    Given a customer named "Alice"
    And a merchant named "Bob"
    And the customer has a bank account with balance 500.0
    And the merchant has a bank account with balance 0.0
    When the customer gets 1 tokens
    And the merchant requests a payment of 100 kr
    When the merchant requests a report
    Then the merchant should see a report with the following transaction details

    Scenario: Generate a report for a manager
      Given a manager
      When the manager requests a report
      Then the manager should see a report with the following transaction details


    Scenario: Generate a report for a customer with multiple transactions




  Scenario: Generate a report for a customer with no transactions
    Given a customer with name "Alice" with CPR number "0987654321" and bank account balance 1500 kr
    And a merchant with name "Lars Larsen" with CPR number "12345678" and account balance 2000 kr
    When the customer gets 5 tokens
    When the customer requests a report
    Then the customer should see a report with the following transaction details