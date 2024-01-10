Feature:
  Scenario: Merchant has been assigned
    Given a merchant
      And a token
    When the merchant requests a payment for 10 kr
    Then the "MerchantAccountNoIsAssigned" event is sent

  Scenario: Customer has been assigned
    Given a payment request
    When token is in the list of tokens
    Then the "CustomerIsAssigned" event is sent

  Scenario: Customer Bank Account Assigned
    Given a customer "String name" with cpr no "String cpr"
    And an unassigned bank account with account no "String accountNo"
    When the customer is assigned to the bank account "String accountNo"
    Then the customer's bank account is assigned
    And the customer's account no is "String accountNo"

  Scenario: Successful payment
  Given a customer "String name" with cpr no "String cpr"
    And customer balance "int" kr is registered in a bank
    And a merchant "String name" with cpr no "String cpr"
    And merchant balance "int" kr is registered in a bank
    And customer and merchant is registered in DTU pay
  When the merchant requests a payment for 10 kr
  Then payment transaction is succesful
    And the customer balance is "int" kr
    And the merchant balance is "int" kr

#  Scenario: insuffcient customer balance
#    Given a customer "String name" with cpr no "String cpr"
#    And customer balance "int" kr is registered in a bank
#    And a merchant "String name" with cpr no "String cpr"
#    And merchant balance "int" kr is registered in a bank
#    And customer and merchant is registered in DTU pay
#    When the merchant requests a payment for 10 kr
#    Then payment transaction is unsuccesful
#    And the customer balance is "int" kr
#    And the merchant balance is "int" kr





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

