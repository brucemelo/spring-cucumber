# Spring Cucumber

A Spring Boot 3 application with Cucumber 7 integration for BDD (Behavior-Driven Development) testing.

## Project Overview

This application demonstrates a student and course management system with the following features:
- Student management (create, find)
- Course management (create, find)
- Student enrollment in courses

The project uses:
- Spring Boot 3.5.3
- Java 21
- Cucumber 7.15.0 for BDD testing
- Spring Data JPA with H2 in-memory database
- Gradle for build management

## Getting Started

### Prerequisites
- Java 21 or higher
- Gradle (or use the included Gradle wrapper)

### Running the Application

To run the application:

```bash
./gradlew bootRun
```

The application will start on port 8080 by default.

## Running Tests

### Running All Tests

To run all tests including Cucumber tests:

```bash
./gradlew test
```

### Running Only Cucumber Tests

To run only the Cucumber tests:

```bash
./gradlew test --tests "com.brucemelo.springcucumber.CucumberTest"
```

## Cucumber Reports

After running the tests, Cucumber generates HTML reports that provide detailed information about test execution, including:
- Feature overview
- Scenario results (passed/failed)
- Step details
- Execution time

### Viewing Cucumber Reports

The HTML reports are generated at:
```
build/cucumber-reports/report.html
```

To view the reports:
1. Run the tests using one of the commands above
2. Open the HTML report in your browser:
   ```bash
   open build/cucumber-reports/report.html  # On macOS
   # OR
   xdg-open build/cucumber-reports/report.html  # On Linux
   # OR
   start build/cucumber-reports/report.html  # On Windows
   ```

## Feature Overview

The application has two main features tested with Cucumber:

### Student Management
- Creating new students
- Finding students by email
- Handling cases when students are not found

### Course Management
- Creating new courses
- Finding courses by code
- Enrolling students in courses
- Managing multiple course enrollments for students

For detailed scenarios, see the feature files in `src/test/resources/features/`.
