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
      | name          | email                  |
      | John Doe      | john.doe@example.com   |
      | Jane Smith    | jane.smith@example.com |

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
    When I enroll student "john.doe@example.com" in course "CS101"
    Then student "john.doe@example.com" should be enrolled in course "CS101"
    
  Scenario: Student enrolls in multiple courses
    When I enroll student "jane.smith@example.com" in course "CS101"
    And I enroll student "jane.smith@example.com" in course "CS201"
    Then student "jane.smith@example.com" should be enrolled in 2 courses