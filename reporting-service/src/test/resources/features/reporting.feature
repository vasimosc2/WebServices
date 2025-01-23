Feature: Reporting Service
    Scenario: Reporting of transactions manager
        Given a list of transactions saved in the repository
        When a request to see all transactions is made by the manager
        Then the list of transactions is shown




