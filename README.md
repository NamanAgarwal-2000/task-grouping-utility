# Task Utility

This is a simple Java utility project.

It reads problem data from a JSON file and generates a progress report based on category and status.

## Features

- Read problems from JSON file
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

```
Main.java
```

## Run Tests

Run:

```
ProblemProgressReportGeneratorTest.java
```

Or use:

```bash
mvn test
```

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

