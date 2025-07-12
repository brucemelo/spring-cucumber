Feature: Student Management
  As an administrator
  I want to manage students
  So that I can keep track of enrolled students

  Background:
    Given the following students exist:
      | name          | email                    |
      | Bruce Melo    | bruce.melo@example.com   |
      | Maria Souza   | maria.souza@example.com  |

  Scenario: Create a new student
    When I create a student with name "Bob Johnson" and email "bob.johnson@example.com"
    Then the student with email "bob.johnson@example.com" should exist
    And the student should have name "Bob Johnson"

  Scenario: Find student by email
    When I search for a student with email "bruce.melo@example.com"
    Then I should find a student
    And the student should have name "Bruce Melo"

  Scenario: Student not found
    When I search for a student with email "nonexistent@example.com"
    Then I should not find a student