Feature:
  # Author: Fuad Hassan Jama (s233468)
  Scenario: Perform a successful payment
    Given a customer named "Alice"
      And a merchant named "Bob"
      And the customer has a bank account with balance 1000.0
      And the merchant has a bank account with balance 0.0
      And the customer is registered with DTU Pay
      And the merchant is registered with DTU Pay
    When the customer gets 1 tokens
      And the merchant requests a payment of 10 kr
      And the customer grants the payment with a token
    Then the customer has balance 990.0
      And the merchant has balance 10.0

  # Author: Muhamad Hussein Nadali (s233479)
  Scenario: Perform concurrent payments
    Given a registered merchant with a balance of 0.0
      And 10 registered customers with a balance of 1000.0 kr
      And each customer has 1 unused tokens
    When the merchant initiates a payment between 10.0 kr and 500.0 kr for each customer
    Then the payments should have been assigned the correct bank account numbers
      And the customers should have deducted the correct amount of money
      And the merchant should have received the correct amount of money

  # Author: Clair Norah Mutebi (s184187)
  Scenario: Insufficient customer balance
    Given a customer named "Alice"
      And a merchant named "Bob"
      And the customer has a bank account with balance 0.0
      And the merchant has a bank account with balance 0.0
      And the customer is registered with DTU Pay
      And the merchant is registered with DTU Pay
    When the customer gets 1 tokens
      And the merchant requests a payment of 10 kr
      And the customer grants the payment with a token
    Then the error with message "Insufficient account balance" is received

  # Author: Alexander Matzen (s233475)
  Scenario: Customer bank account does not exist
    Given a customer named "Alice"
      And a merchant named "Bob"
      And the customer has a bank account, that does not exist
      And the merchant has a bank account with balance 0.0
      And the customer is registered with DTU Pay
      And the merchant is registered with DTU Pay
    When the customer gets 1 tokens
      And the merchant requests a payment of 10 kr
      And the customer grants the payment with a token
    Then the error with message "No such debtor account" is received

  # Author: Alexander Matzen (s233475)
  Scenario: Merchant bank account does not exist
    Given a customer named "Alice"
      And a merchant named "Bob"
    And the customer has a bank account with balance 1000.0
      And the merchant has a bank account, that does not exist
      And the customer is registered with DTU Pay
      And the merchant is registered with DTU Pay
    When the customer gets 1 tokens
      And the merchant requests a payment of 10 kr
      And the customer grants the payment with a token
    Then the error with message "No such creditor account" is received

  # Author: Dilara Eda Celepli (s184262)
  Scenario: Payment granted with invalid token
    Given a customer named "Alice"
      And a merchant named "Bob"
      And the customer has a bank account with balance 1000.0
      And the merchant has a bank account with balance 0.0
      And the customer is registered with DTU Pay
      And the merchant is registered with DTU Pay
      And the customer has an invalid token
    When the merchant requests a payment of 10 kr
      And the customer grants the payment with a token
    Then the error with message "Invalid token" is received

  # Author: Esben Damkjær Sørensen (s233474)
  Scenario: Payment granted with previously used token
    Given a customer named "Alice"
      And a merchant named "Bob"
      And the customer has a bank account with balance 1000.0
      And the merchant has a bank account with balance 0.0
      And the customer is registered with DTU Pay
      And the merchant is registered with DTU Pay
    When the customer gets 1 tokens
      And the merchant requests a payment of 10 kr
      And the customer grants the payment with a token without discarding it
      And the merchant requests a payment of 10 kr
      And the customer grants the payment with a token without discarding it
    Then the error with message "Invalid token" is received