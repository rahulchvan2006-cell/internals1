# Student Grade Calculator (maven-github-demo)

A Java-based Student Grade Calculator application designed according to Experiment 3 specifications. It accepts marks obtained by a student across 5 subjects, calculates total marks, percentage average, and assigns the appropriate grade.

Includes automated JUnit 5 test cases and a GitHub Actions workflow (`maven.yml`) that compiles the application, runs all test cases, and automatically builds and pushes a Docker image to Docker Hub upon push.

---

## Project Structure (Experiment 3 Layout)

```text
student-grade-calculator/
├── .github/
│   └── workflows/
│       └── maven.yml               # GitHub Actions Workflow
├── .mvn/
│   └── wrapper/
│       └── maven-wrapper.properties
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── example/
│   │               └── App.java     # Student Grade Calculator Application
│   └── test/
│       └── java/
│           └── com/
│               └── example/
│                   └── AppTest.java # JUnit 5 Test Suite
├── Dockerfile                      # Multi-stage Dockerfile
├── .dockerignore                   # Docker ignore file
├── .gitignore                      # Git ignore file
├── pom.xml                         # Maven Project Descriptor (com.example:maven-github-demo)
└── README.md                       # Documentation
```

---

## Grading Criteria

| Average Percentage Range | Final Grade | Classification |
|:------------------------|:-----------:|:---------------|
| 90.00% – 100.00%        | **A**       | Outstanding / Excellent |
| 80.00% – 89.99%         | **B**       | Very Good |
| 70.00% – 79.99%         | **C**       | Good |
| 60.00% – 69.99%         | **D**       | Satisfactory |
| Below 60.00%            | **F**       | Fail |

---

## Commands to Run Locally

### 1. Run JUnit 5 Tests
```bash
mvn clean test
```

### 2. Compile and Package JAR
```bash
mvn clean package
```
Generates `target/maven-github-demo-1.0-SNAPSHOT.jar`.

### 3. Run Application
```bash
java -jar target/maven-github-demo-1.0-SNAPSHOT.jar
```

---

## Push to GitHub Repository

This repository is already configured with your remote:
`https://github.com/rahulchvan2006-cell/internals1.git`

To push the code to your repository:
```bash
git push -u origin main
```

---

## Docker Hub Configuration in GitHub Actions

To enable automatic Docker image pushing in the `maven.yml` workflow:
1. Go to your GitHub repository: **Settings** > **Secrets and variables** > **Actions**.
2. Add the following secrets:
   - `DOCKERHUB_USERNAME`: Your Docker Hub username.
   - `DOCKERHUB_TOKEN`: Your Docker Hub Personal Access Token (PAT).
