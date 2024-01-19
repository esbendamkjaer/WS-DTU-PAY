Feature:
  # Author: Esben
  Scenario: Successful payment
    When a PaymentRequest has been received
    Then a PaymentInitiatedEvent is sent
    When a CustomerBankAccountAssignedEvent is received
      And a MerchantBankAccountAssignedEvent is received
    Then the bank is asked to transfer the money
      And a corresponding PaymentTransferredEvent is sent

  # Author: Muhamad
  Scenario: Invalid token triggers payment cancellation
    When a PaymentRequest has been received
    Then a PaymentInitiatedEvent is sent
    When a TokenInvalidatedEvent is received
      And a MerchantBankAccountAssignedEvent is received
    Then a corresponding PaymentFailedEvent is sent with cause "Invalid token"
