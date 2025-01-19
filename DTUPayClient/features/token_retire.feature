Feature: Token Usage

  Scenario: A user generates a token, validates it, and then retires it
    Given a user with customerId "C123"
    When the user requests 1 token
    And the user retrieves the list of all tokens
    Then user "C123" should have exactly 1 unused token
    When the user validates that token
    When dtupay retires that token