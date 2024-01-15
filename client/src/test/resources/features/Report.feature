Feature: Report feature

  Scenario: Generate a report for a customer
    Given a customer with name "Hubert Jensen" with CPR number "0101011111" and account balance 1400 kr
    And a merchant with name "Lars Larsen" with CPR number "12345678" and account balance 2000 kr
    And a customer requests 5 tokens
    And a merchant requests a payment of 100 kr from the customer
    When the customer requests a report
    Then the customer should see a report with the following transaction details


  Scenario: Generate a report for a merchant
    Given a customer with name "Hubert Jensen" with CPR number "0101011111" and account balance 1400 kr
    And a merchant with name "Lars Larsen" with CPR number "12345678" and account balance 2000 kr
    And a customer requests 5 tokens
    And a merchant requests a payment of 100 kr from the customer
    When the merchant requests a report
    Then the merchant should see a report with the following transaction details