Feature:
  Scenario: Check account balance
    Given I have deposited 100 in my account
    When I check my balance
    Then I should see that my balance is 100