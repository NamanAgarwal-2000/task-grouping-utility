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

## Run Tests

```bash
mvn test
```

This runs both unit and integration tests.

**Java Version:** 17

## CLI Options

| Option       | Description                             |
| ------------ | --------------------------------------- |
| --input      | Input CSV or JSON file path             |
| --output     | Output JSON report file path            |
| --help       | Show help information                   |
| --status     | Filter by status (completed/pending)    |
| --category   | Filter by category                      |
| --difficulty | Filter by difficulty (Easy/Medium/Hard) |
| --sort-by    | Sort by title, time, or difficulty      |
| --sort-order | Sort order (asc/desc)                   |

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
  "validCount" : 3,
  "invalidCount" : 0
}

```

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


