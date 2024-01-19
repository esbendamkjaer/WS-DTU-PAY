Feature:
  # Author: Fuad
  Scenario: Customer requests 5 tokens
    Given a customer named "Alice"
      And the customer has a bank account with balance 5000.0
      And the customer is registered with DTU Pay
    When the customer gets 5 tokens
    Then the customer has 5 unused tokens

  # Author: Muhamad
  Scenario: Customer requests additional tokens, surpassing his allowed amount
    Given a customer named "Alice"
      And the customer has a bank account with balance 5000.0
      And the customer is registered with DTU Pay
    When the customer gets 7 tokens
    Then the error with message "Token amount exceeds allowed limit" is received

  # Author: Alexander
  Scenario: Customer requests additional tokens
    Given a customer named "Alice"
      And the customer has a bank account with balance 5000.0
      And the customer is registered with DTU Pay
    When the customer gets 1 tokens
      And the customer gets 5 tokens
    Then the customer has 6 unused tokens

  # Author: Clair
  Scenario: Customer has more than 1 unused token and asks for more
    Given a customer named "Alice"
      And the customer has a bank account with balance 5000.0
      And the customer is registered with DTU Pay
    When the customer gets 2 tokens
      And the customer gets 2 tokens
    Then the error with message "User has more than one unused token" is received

  # Author: Dilara
  Scenario: Customer requests -1 token
    Given a customer named "Alice"
      And the customer has a bank account with balance 5000.0
      And the customer is registered with DTU Pay
    When the customer gets -1 tokens
    Then the error with message "Illegal number of tokens requested" is received