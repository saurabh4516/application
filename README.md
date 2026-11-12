# Jenkins CI/CD Pipeline Automation

## Project Overview

This project demonstrates Continuous Integration and Continuous Deployment (CI/CD) using Jenkins, Git, Maven, and JUnit.

The pipeline automatically checks the GitHub repository for code changes, builds the Java application, executes automated tests, generates a code coverage report, and deploys the application to a staging environment when the pipeline succeeds.

## Technologies Used

- Jenkins – CI/CD automation
- Git and GitHub – Version control
- Java 17 – Application development
- Maven – Build automation
- JUnit – Automated testing
- JaCoCo – Code coverage reporting
- Linux (Ubuntu WSL2) – Jenkins environment

## Pipeline Stages

1. **Checkout:** Retrieves source code from GitHub.
2. **Build:** Builds the Java application using Maven.
3. **Test:** Executes automated JUnit test cases.
4. **Coverage Report:** Generates and publishes the JaCoCo HTML coverage report.
5. **Deploy (staging):** Deploys the application to the staging environment.
6. **Health Check:** Verifies that the deployed application is responding.

## Automatic Trigger

Jenkins uses SCM polling to check the GitHub repository for changes every two minutes.

```groovy
pollSCM('H/2 * * * *')
```

## Test Results

- Total JUnit tests: 9
- Passed: 9
- Failed: 0
- Errors: 0

## Repository

GitHub: https://github.com/saurabh4516/application

## Outcome

Successfully configured and executed a Jenkins CI/CD pipeline with automated source code checkout, Maven build, JUnit testing, code coverage reporting, and staging deployment.
