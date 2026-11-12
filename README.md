# Student Grade Calculator

A Java-based Student Grade Calculator application designed to accept marks obtained by a student across 5 subjects, calculate total marks, percentage average, and assign the appropriate grade. The project includes automated JUnit 5 test cases and a production-grade CI/CD GitHub Actions workflow that compiles the application, runs tests, and upon passing, automatically builds and pushes a Docker image to Docker Hub.

---

## Table of Contents
- [Features](#features)
- [Grading Criteria](#grading-criteria)
- [Project Architecture & Directory Structure](#project-architecture--directory-structure)
- [Prerequisites](#prerequisites)
- [Local Setup & Execution](#local-setup--execution)
- [Running JUnit 5 Tests](#running-junit-5-tests)
- [Running with Docker](#running-with-docker)
- [CI/CD GitHub Actions Workflow](#cicd-github-actions-workflow)
  - [Docker Hub Secrets Configuration](#docker-hub-secrets-configuration)
  - [Workflow Stages](#workflow-stages)

---

## Features
- **5-Subject Input**: Accepts marks obtained out of 100 for 5 distinct subjects.
- **Robust Validation**: Ensures all inputs are non-null, exactly 5 subjects are provided, and each mark is within the valid range ($0.0 \le \text{mark} \le 100.0$).
- **Calculations**:
  - **Total Marks**: Sum of all 5 subjects (maximum 500.00).
  - **Average Percentage**: Accurate floating-point calculation ($\frac{\text{Total}}{5}$).
  - **Grade Assignment**: Deterministic letter grades based on standard academic thresholds.
- **Interactive CLI**: Friendly console prompts with real-time error handling and re-prompting for invalid entries.
- **Full Unit Test Suite**: Comprehensive JUnit 5 parameterized and edge-case unit tests.
- **Containerization**: Multi-stage lightweight Docker image using Eclipse Temurin JRE Alpine.
- **Automated CI/CD**: GitHub Actions workflow for test automation and automated Docker Hub deployment.

---

## Grading Criteria

| Average Percentage Range | Final Grade | Classification |
|:------------------------|:-----------:|:---------------|
| 90.00% – 100.00%        | **A**       | Excellent      |
| 80.00% – 89.99%         | **B**       | Very Good      |
| 70.00% – 79.99%         | **C**       | Good           |
| 60.00% – 69.99%         | **D**       | Satisfactory   |
| Below 60.00%            | **F**       | Fail           |

---

## Project Architecture & Directory Structure

```text
student-grade-calculator/
├── .github/
│   └── workflows/
│       └── ci-cd.yml                # GitHub Actions CI/CD Pipeline
├── .mvn/
│   └── wrapper/
│       └── maven-wrapper.properties
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── gradecalculator/
│   │               ├── Main.java                        # Interactive CLI App
│   │               ├── model/
│   │               │   └── StudentResult.java           # Result Data Model & Report Formatter
│   │               └── service/
│   │                   └── GradeCalculatorService.java  # Core Logic & Calculations
│   └── test/
│       └── java/
│           └── com/
│               └── gradecalculator/
│                   └── service/
│                       └── GradeCalculatorServiceTest.java # JUnit 5 Test Suite
├── Dockerfile                       # Multi-stage Docker build
├── .dockerignore                    # Docker build ignore patterns
├── .gitignore                       # Git ignore patterns
├── pom.xml                          # Maven build & dependencies descriptor
└── README.md                        # Documentation
```

---

## Prerequisites
- **Java Development Kit (JDK)**: Version 17 or higher
- **Apache Maven**: Version 3.8+ (or Maven Wrapper)
- **Docker**: Optional (for containerized execution)
- **Git**: For version control

---

## Local Setup & Execution

### 1. Build the Application
```bash
mvn clean compile
```

### 2. Package Executable JAR
```bash
mvn package
```

### 3. Run the Application
```bash
java -jar target/student-grade-calculator-1.0.0.jar
```
*Or directly via Maven:*
```bash
mvn exec:java -Dexec.mainClass="com.gradecalculator.Main"
```

#### Sample Interactive Console Session:
```text
=================================================
       STUDENT GRADE CALCULATOR APPLICATION      
=================================================
Enter Student Name (or press Enter for default): John Doe

Please enter marks obtained (0 to 100) for 5 subjects:
Enter marks for Subject 1 (Mathematics): 88.5
Enter marks for Subject 2 (Science): 92.0
Enter marks for Subject 3 (English): 84.0
Enter marks for Subject 4 (Social Studies): 79.5
Enter marks for Subject 5 (Computer Science): 95.0

========================================
         STUDENT GRADE REPORT          
========================================
 Student Name     : John Doe
 ----------------------------------------
 Subject 1        : 88.50 / 100.00
 Subject 2        : 92.00 / 100.00
 Subject 3        : 84.00 / 100.00
 Subject 4        : 79.50 / 100.00
 Subject 5        : 95.00 / 100.00
 ----------------------------------------
 Total Marks      : 439.00 / 500.00
 Average Marks    : 87.80%
 Final Grade      : B
========================================

Thank you for using the Student Grade Calculator!
```

---

## Running JUnit 5 Tests

The test suite in `GradeCalculatorServiceTest.java` verifies all calculation paths and collections:
- **Total Marks Tests**: Standard marks, boundary all 100s, boundary all 0s, decimal marks.
- **Average Marks Tests**: Precision checks, min/max calculations.
- **Grade Assignment Tests**: Parameterized boundary testing across all grade bands (A, B, C, D, F).
- **Validation Tests**: Null collections, lists with fewer than 5 subjects, lists with more than 5 subjects, negative marks, marks > 100, and null elements.
- **Workflow Tests**: End-to-end result construction and formatted card validation.

Execute the test suite:
```bash
mvn test
```

Test reports are generated in `target/surefire-reports/`.

---

## Running with Docker

### 1. Build the Docker Image
```bash
docker build -t student-grade-calculator:latest .
```

### 2. Run the Docker Container Interactively
```bash
docker run -it --rm student-grade-calculator:latest
```

---

## CI/CD GitHub Actions Workflow

The automated workflow located in [`.github/workflows/ci-cd.yml`](.github/workflows/ci-cd.yml) executes on every `push` and `pull_request` to the `main` or `master` branch.

### Docker Hub Secrets Configuration
To allow GitHub Actions to authenticate and push the Docker image to your Docker Hub repository:
1. Navigate to your GitHub repository: **Settings** > **Secrets and variables** > **Actions**.
2. Click **New repository secret** and add the following two secrets:
   - `DOCKERHUB_USERNAME`: Your Docker Hub account username.
   - `DOCKERHUB_TOKEN`: A Docker Hub Personal Access Token (PAT) with `Read & Write` permissions (create one at [Docker Hub Account Settings > Security](https://hub.docker.com/settings/security)).

### Workflow Stages
1. **`compile-and-test`**:
   - Checks out the repository.
   - Configures JDK 17 (Temurin) with Maven dependency caching.
   - Compiles all Java source files.
   - Executes all JUnit 5 test cases (`mvn clean test`).
   - Packages the application JAR artifact (`mvn package`).
   - Archives JUnit test reports as build artifacts.
2. **`build-and-push-docker`** (triggers automatically only when all tests pass on `push` to `main`/`master`):
   - Sets up Docker Buildx and QEMU.
   - Securely logs into Docker Hub using repository secrets.
   - Extracts Docker image tags (`latest`, commit SHA).
   - Builds the production multi-stage Docker image.
   - Automatically pushes the tagged image to Docker Hub (`<username>/student-grade-calculator`).
