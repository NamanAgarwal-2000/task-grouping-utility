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
- Added unit and integration tests

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
mvn exec:java -Dexec.args="src/main/resources/problems.csv output/report.json"
```

## Run Tests
Run:

```bash
mvn test
```
This runs both unit tests and integration tests

Requires Java 17

## Sample Output
```json
{
  "reportSummary" : {
    "completedProblems" : 3,
    "pendingProblems" : 2,
    "totalProblems" : 5,
    "totalTimeSpent" : "5h 45m",
    "difficultySummary" : {
      "Easy" : 1,
      "Medium" : 2,
      "Hard" : 2
    },
    "groupedResult" : {
      "Array" : {
        "pending" : 1,
        "completed" : 1
      },
      "Graph" : {
        "completed" : 1
      },
      "Tree" : {
        "pending" : 1
      },
      "DP" : {
        "completed" : 1
      }
    }
  },
  "invalidRecords" : [ ],
  "validCount" : 5,
  "invalidCount" : 0
}
```
## Expected CSV Format

The following headers are mandatory:

```csv
title,category,difficulty,status,timeSpentMinutes
```

Example:

```csv
Two Sum,Array,Easy,done,30
Binary Tree,Tree,Medium,pending,60
Graph Traversal,Graph,Hard,completed,120
```

### Quoted Values

Fields containing commas should be wrapped in quotes.

Example:

```csv
title,category,difficulty,status,timeSpentMinutes
"Graph, BFS Basics",Graph,Medium,completed,45
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
- Missing category validation with invalid record reporting
---

## New Features Added

- Added CSV input support
- Added JSON report export
- Auto-detect input type using file extension
- Graceful handling for invalid records
- Added tests for CSV and JSON support
- Added invalid record reporting support

---

## Export JSON Report

Example:


```bash
mvn exec:java -Dexec.args="src/main/resources/problems.json output/report.json"
mvn exec:java -Dexec.args="src/main/resources/problems.csv output/report.json"
```

Generated report:

```json
{
  "reportSummary" : {
    "completedProblems" : 3,
    "pendingProblems" : 2,
    "totalProblems" : 5,
    "totalTimeSpent" : "5h 45m",
    "difficultySummary" : {
      "Easy" : 1,
      "Medium" : 2,
      "Hard" : 2
    },
    "groupedResult" : {
      "Array" : {
        "pending" : 1,
        "completed" : 1
      },
      "Graph" : {
        "completed" : 1
      },
      "Tree" : {
        "pending" : 1
      },
      "DP" : {
        "completed" : 1
      }
    }
  },
  "invalidRecords" : [ ],
  "validCount" : 5,
  "invalidCount" : 0
}
```
## Integration Tests

The integration tests verify the complete application flow:

- Read CSV/JSON input
- Validate records
- Generate progress report
- Export report to JSON

Tests generate fresh output files during execution and do not depend on any pre-existing report.json file.