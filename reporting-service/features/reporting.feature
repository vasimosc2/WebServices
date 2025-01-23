Feature: Reporting Service

    Scenario: Reporting of transactions customer
        Given a list of transactions saved in the repository
        When a request to see all transactions is made by the customer "CUST-1"
        Then the list of transactions for the customer is shown

    Scenario: Reporting of transactions customer
        Given a list of transactions saved in the repository
        When a request to see all transactions is made by the merchant "MERC-1"
        Then the list of transactions for the merchant is shown

    Scenario: Reporting of transactions manager
        Given a list of transactions saved in the repository
        When a request to see all transactions is made by the manager
        Then the list of transactions is shown







