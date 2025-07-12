Feature: Course Management
  As an administrator
  I want to manage courses
  So that I can offer various courses to students

  Background:
    Given the following courses exist:
      | name                    | code    | credits |
      | Introduction to Java    | CS101   | 3       |
      | Advanced Programming    | CS201   | 4       |
    And the following students exist:
      | name          | email                   |
      | Bruce Melo    | bruce.melo@example.com  |
      | Maria Souza   | maria.souza@example.com |

  Scenario: Create a new course
    When I create a course with name "Data Structures", code "CS301" and credits 4
    Then the course with code "CS301" should exist
    And the course should have name "Data Structures"
    And the course should have 4 credits

  Scenario: Find course by code
    When I search for a course with code "CS101"
    Then I should find a course
    And the course should have name "Introduction to Java"

  Scenario: Enroll student in a course
    When I enroll student "bruce.melo@example.com" in course "CS101"
    Then student "bruce.melo@example.com" should be enrolled in course "CS101"
    
  Scenario: Student enrolls in multiple courses
    When I enroll student "maria.souza@example.com" in course "CS101"
    And I enroll student "maria.souza@example.com" in course "CS201"
    Then student "maria.souza@example.com" should be enrolled in 2 courses