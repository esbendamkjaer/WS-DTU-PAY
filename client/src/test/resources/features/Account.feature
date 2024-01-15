Feature:
  Scenario: Customer registration
    Given a customer named "Baumeister"
    And the customer has a bank account with balance 1000.0
    And the customer is registered with DTU Pay
    Then the customer is assigned an id
    And the customer has balance 1000.0

  Scenario: Merchant registration
    Given a merchant named "Hubert"
    And the merchant has a bank account with balance 1000.0
    And the merchant is registered with DTU Pay
    Then the merchant is assigned an id
    And the merchant has balance 1000.0