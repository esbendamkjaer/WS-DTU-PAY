Scenario: Customer requests 5 tokens
  Given a costumer "Hubert" with bank account balance 5000 dkk
  And the customer is registered in DTU Pay
  And the customer has 0 tokens
  When the customer requests 5 tokens
  Then the customer has 5 unused tokens

Scenario: Customer requests additional tokens, surpassing his allowed amount
  Given a customer "Hubert" with bank account balance 5000DK
  And the customer is registered in DTU Pay
  And the customer has 1 tokens
  When the customer requests 6 tokens
  Then the customer receives an exception

Scenario: Customer requests additional tokens
  Given a costumer "Hubert" with bank account balance 5000DK
  And the customer is registered in DTU Pay
  And the customer has 1 tokens
  When the customer requests 5 tokens
  Then the customer has 6 unused tokens


Scenario: Customer has more than 1 unused token and asks for more
  Given a costumer "Hubert" with bank account balance 5000DK
  And the customer is registered in DTU Pay
  And the customer has 3 tokens
  When the customer requests 2 tokens
  Then the customer receives an exception

Scenario: Customer requests -1 token
  Given a costumer "Hubert" with bank account balance 5000DK
  And the customer is registered in DTU Pay
  When the customer requests -1 tokens
  Then the customer receives an exception

Scenario: Token is used more than once
  Given a token is not in the list
  When the has to be validated
  Then the token is not validated