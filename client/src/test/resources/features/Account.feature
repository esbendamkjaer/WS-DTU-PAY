Feature:
  # Author: Alexander
  Scenario: Customer registration
    Given a customer named "Alice"
    And the customer has a bank account with balance 1000.0
    And the customer is registered with DTU Pay
    Then the customer is assigned an id
    And the customer has balance 1000.0

  # Author: Clair
  Scenario: Merchant registration
    Given a merchant named "Bob"
    And the merchant has a bank account with balance 1000.0
    And the merchant is registered with DTU Pay
    Then the merchant is assigned an id

  # Author: Dilara
  Scenario: Get customer by id
    Given a customer named "Alice"
    And the customer has a bank account with balance 1000.0
    And the customer is registered with DTU Pay
    When the customer is retrieved by id
    Then expect the same customer

  # Author: Esben
  Scenario: Deregister customer
    Given a customer named "Alice"
    And the customer has a bank account with balance 1000.0
    And the customer is registered with DTU Pay
    When the customer deregisters
    Then the customer is no longer registered with DTU Pay