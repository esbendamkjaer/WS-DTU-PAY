Feature:
  #@author Esben Damkjær Sørensen (s233474)
  Scenario: Payment initiated
    Given a valid token
    When a PaymentInitiatedEvent is received
    Then a corresponding TokenValidatedEvent is sent
  #@author Alexander Matzen (s233475)
  Scenario: Payment initiated with invalid token
    Given an invalid token
    When a PaymentInitiatedEvent is received
    Then a corresponding TokenInvalidatedEvent is sent
    