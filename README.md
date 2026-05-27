# Task Utility

This is a simple Java utility project.

It reads problem data from JSON and CSV files and generates a progress report.

## Features

- Read problems from JSON and CSV files
- Export report to JSON file
- Group problems by category
- Count completed and pending problems
- Show difficulty summary
- Calculate total time spent
- Basic JUnit tests added

## Project Structure

```
src
 ├── main
 │    ├── java
 │    └── resources
 │
 └── test
```

## Main Classes

- Main.java
- Problem.java
- ProblemJsonReader.java
- ProblemProgressReportGenerator.java

## Run Project

Run:

```bash
mvn exec:java -Dexec.args="src/main/resources/problems.json"
```

## Run Tests

Run:

```bash
mvn test
```

Requires Java 17

## Sample Output

```
Completed Problems: 3
Pending Problems: 2

Difficulty Summary:
{Easy=1, Medium=2, Hard=2}

Total Problems: 5

Total Time Spent: 5h 45m
```

## Edge Cases Handled

- Empty input
- Null category
- Blank status
- Missing values

- Invalid CSV rows
- Invalid number in CSV
- Negative time handling
- Status normalization (done/completed/pending)

---

## New Features Added

- Added CSV input support
- Added JSON report export
- Auto-detect input type using file extension
- Graceful handling for invalid records
- Added tests for CSV and JSON support

---

## Export JSON Report

Example:


```bash
mvn exec:java -Dexec.args="src/main/resources/problems.csv output/report.json"
```
```

Generated report:

```json
{
  "completedProblems": 1,
  "pendingProblems": 1,
  "totalProblems": 2,
  "totalTimeSpent": "1h 30m"
}
```