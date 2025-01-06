Feature: String Calculator
  As a user
  I want to add numbers using a StringCalculator
  So that I can calculate sums easily

  Scenario: Adding no numbers
    Given a StringCalculator
    When I add ""
    Then the result should be 0

  Scenario: Adding one number
    Given a StringCalculator
    When I add "1"
    Then the result should be 1

  Scenario: Adding two numbers
    Given a StringCalculator
    When I add "1,2"
    Then the result should be 3

  Scenario: Adding unknown amount of numbers
    Given a StringCalculator
    When I add "1,2,3,4,5"
    Then the result should be 15

  Scenario: Adding numbers with newlines
    Given a StringCalculator
    When I add "1\n2,3"
    Then the result should be 6

  Scenario: Adding numbers with custom delimiter
    Given a StringCalculator
    When I add "//;\n1;2"
    Then the result should be 3

  Scenario: Adding negative numbers throws exception
    Given a StringCalculator
    When I add "1,-2,-3"
    Then an exception with message "Negatives not allowed: -2, -3" should be thrown
