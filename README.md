# Task Utility

A simple Java utility that reads problem data from CSV or JSON files and generates a progress report.

The utility supports filtering and sorting through CLI options and exports the generated report as a JSON file.

## Features

* Read problem data from CSV and JSON files
* Generate progress reports
* Export reports to JSON
* Group problems by category
* Count completed and pending problems
* Generate difficulty summaries
* Calculate total time spent
* Filter problems using CLI options
* Sort results by title, difficulty, or time spent
* Unit and integration test coverage

## Build

```bash
mvn clean package
```

## Project Structure

```text
src
├── main
│   ├── java
│   └── resources
└── test
```

## Run Project

Generate a report from a CSV file:

```bash
java -jar target/task-utility-1.0-SNAPSHOT.jar \
  --input src/main/resources/problems.csv \
  --output output/report.json
```

Generate a report from a JSON file:

```bash
java -jar target/task-utility-1.0-SNAPSHOT.jar \
  --input src/main/resources/problems.json \
  --output output/report.json
```
## Spring Boot API

### Run Application

```bash
mvn spring-boot:run
```

The application starts on:

```text
http://localhost:8080
```

### Health Check

```bash
curl http://localhost:8080/health
```

Response:

```text
OK
```

### Generate Report

```bash
curl -X POST http://localhost:8080/reports \
-H "Content-Type: application/json" \
-d '{
  "problems": [
    {
      "title": "Two Sum",
      "status": "Completed",
      "category": "Array",
      "difficulty": "Easy",
      "timeSpentMinutes": 30
    }
  ]
}'
```

Sample Response:

```json
{
  "completedProblems": 1,
  "pendingProblems": 0,
  "totalProblems": 1,
  "totalTimeSpent": "0h 30m",
  "difficultySummary": {
    "Easy": 1
  },
  "groupedResult": {
    "Array": {
      "completed": 1
    }
  }
}
```


## Run Tests

```bash
mvn test
```

This runs both unit and integration tests.

**Java Version:** 17

## CLI Options

| Option       | Description                              |
| ------------ |------------------------------------------|
| --input      | Input CSV or JSON file path              |
| --output     | Output JSON report file path             |
| --help       | Show help information                    |
| --status     | Filter by status (completed/pending/all) |
| --category   | Filter by category                       |
| --difficulty | Filter by difficulty (Easy/Medium/Hard)  |
| --sort-by    | Sort by title, time, or difficulty       |
| --sort-order | Sort order (asc/desc)                    |

## Sample Commands

Show help:

```bash
java -jar target/task-utility-1.0-SNAPSHOT.jar --help
```

Generate report using CSV input:

```bash
java -jar target/task-utility-1.0-SNAPSHOT.jar \
  --input src/main/resources/problems.csv \
  --output output/report.json
```

Generate report using JSON input:

```bash
java -jar target/task-utility-1.0-SNAPSHOT.jar \
  --input src/main/resources/problems.json \
  --output output/report.json
```

## Filtering Examples

Filter completed problems:

```bash
java -jar target/task-utility-1.0-SNAPSHOT.jar \
  --input src/main/resources/problems.csv \
  --output output/report.json \
  --status completed
```

Filter by category:

```bash
java -jar target/task-utility-1.0-SNAPSHOT.jar \
  --input src/main/resources/problems.csv \
  --output output/report.json \
  --category Array
```

Filter by difficulty:

```bash
java -jar target/task-utility-1.0-SNAPSHOT.jar \
  --input src/main/resources/problems.csv \
  --output output/report.json \
  --difficulty Easy
```

Apply multiple filters:

```bash
java -jar target/task-utility-1.0-SNAPSHOT.jar \
  --input src/main/resources/problems.csv \
  --output output/report.json \
  --category Array \
  --status completed
```

## Sorting Examples

Sort by title:

```bash
java -jar target/task-utility-1.0-SNAPSHOT.jar \
  --input src/main/resources/problems.csv \
  --output output/report.json \
  --sort-by title
```

Sort by time spent (descending):

```bash
java -jar target/task-utility-1.0-SNAPSHOT.jar \
  --input src/main/resources/problems.csv \
  --output output/report.json \
  --sort-by time \
  --sort-order desc
```

Sort by difficulty:

```bash
java -jar target/task-utility-1.0-SNAPSHOT.jar \
  --input src/main/resources/problems.csv \
  --output output/report.json \
  --sort-by difficulty
```

## CSV Format

Required headers:

```csv
title,category,difficulty,status,timeSpentMinutes
```

Example:

```csv
Two Sum,Array,Easy,completed,30
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

## Sample Report Output

```json
{
  "reportSummary" : {
    "completedProblems" : 2,
    "pendingProblems" : 1,
    "totalProblems" : 3,
    "totalTimeSpent" : "3h 30m",
    "difficultySummary" : {
      "Easy" : 1,
      "Medium" : 1,
      "Hard" : 1
    },
    "groupedResult" : {
      "Array" : {
        "completed" : 1
      },
      "Graph" : {
        "completed" : 1
      },
      "Tree" : {
        "pending" : 1
      }
    }
  },
  "invalidRecords" : [ ],
  "problems" : [ {
    "title" : "Two Sum",
    "category" : "Array",
    "difficulty" : "Easy",
    "status" : "completed",
    "timeSpentMinutes" : 30
  }, {
    "title" : "Binary Tree",
    "category" : "Tree",
    "difficulty" : "Medium",
    "status" : "pending",
    "timeSpentMinutes" : 60
  }, {
    "title" : "Graph Traversal",
    "category" : "Graph",
    "difficulty" : "Hard",
    "status" : "completed",
    "timeSpentMinutes" : 120
  } ],
  "validCount" : 3,
  "invalidCount" : 0
}
```
### Report Counts

validCount represents the number of valid records after applying CLI filters.

invalidCount represents the total invalid records found during validation and is not affected by filtering.

The problems array and reportSummary values reflect the final filtered and sorted result after applying CLI options.
## Validation & Edge Cases

The application handles:

* Empty input files
* Missing required fields
* Invalid CSV records
* Invalid status values
* Invalid difficulty values
* Invalid filter values
* Invalid sort fields
* Invalid sort order values
* Multiple filters used together
* Case-insensitive filter inputs
* Missing category validation
* Invalid records reporting

## Test Coverage

Tests cover:

* CSV and JSON input processing
* Report generation
* Export functionality
* CLI argument parsing
* Filtering options
* Sorting options
* Validation scenarios
* Integration flow from input to report generation


