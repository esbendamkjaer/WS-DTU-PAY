Feature:
  Scenario: Perform a successful payment
    Given a customer named "Hubert"
      And a merchant named "Baumeister"
      And the customer has a bank account with balance 1000.0
      And the merchant has a bank account with balance 0.0
      And the customer is registered with DTU Pay
      And the merchant is registered with DTU Pay
    When the customer gets 1 tokens
      And the merchant requests a payment of 10 kr
      And the customer grants the payment with a token
    Then the customer has balance 990.0
      And the merchant has balance 10.0

  Scenario: Perform concurrent payments
    Given a registered merchant with a balance of 0.0
      And 10 registered customers with a balance of 1000.0 kr
      And each customer has 1 unused tokens
    When the merchant initiates a payment between 10.0 kr and 500.0 kr for each customer
    Then the payments should have been assigned the correct bank account numbers
      And the customers should have deducted the correct amount of money
      And the merchant should have received the correct amount of money

  Scenario: Insufficient customer balance
    Given a customer named "Hubert"
      And a merchant named "Baumeister"
      And the customer has a bank account with balance 0.0
      And the merchant has a bank account with balance 0.0
      And the customer is registered with DTU Pay
      And the merchant is registered with DTU Pay
    When the customer gets 1 tokens
      And the merchant requests a payment of 10 kr
      And the customer grants the payment with a token
    Then the error with message "Insufficient account balance" is received

  Scenario: Customer bank account does not exist
    Given a customer named "Hubert"
      And a merchant named "Baumeister"
      And the customer has a bank account, that does not exist
      And the merchant has a bank account with balance 0.0
      And the customer is registered with DTU Pay
      And the merchant is registered with DTU Pay
    When the customer gets 1 tokens
      And the merchant requests a payment of 10 kr
      And the customer grants the payment with a token
    Then the error with message "No such debtor account" is received

  Scenario: Merchant bank account does not exist
    Given a customer named "Hubert"
      And a merchant named "Baumeister"
    And the customer has a bank account with balance 1000.0
      And the merchant has a bank account, that does not exist
      And the customer is registered with DTU Pay
      And the merchant is registered with DTU Pay
    When the customer gets 1 tokens
      And the merchant requests a payment of 10 kr
      And the customer grants the payment with a token
    Then the error with message "No such creditor account" is received

  Scenario: Payment granted with invalid token
    Given a customer named "Hubert"
      And a merchant named "Baumeister"
      And the customer has a bank account with balance 1000.0
      And the merchant has a bank account, that does not exist
      And the customer is registered with DTU Pay
      And the merchant is registered with DTU Pay
      And the customer has an invalid token
    When the merchant requests a payment of 10 kr
      And the customer grants the payment with a token
    Then the error with message "Invalid token" is received

#  Scenario: List of payments
#    Given a customer with id "cid1"
#    And a merchant with id "mid1"
#    Given a successful payment of 10 kr from customer "cid1" to merchant "mid1"
#    When the manager asks for a list of payments
#    Then the list contains a payments where customer "cid1" paid 10 kr to merchant "mid1"

#  Scenario: Customer is not known
#    Given a merchant with id "mid1"
#    When the merchant initiates a payment for 10 kr by the customer with id "cid2"
#    Then the payment is not successful
#    And an error message is returned saying "customer with id cid2 is unknown"

#  Scenario: Merchant is not known
#    Given a customer with id "cid1"
#    When the merchant with id "mid2" initiates a payment for 10 kr by the customer
#    Then the payment is not successful
#    And an error message is returned saying "merchant with id mid2 is unknown"

