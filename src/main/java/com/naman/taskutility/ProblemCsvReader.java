package com.naman.taskutility;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class ProblemCsvReader {

    public ValidationResult readProblems(String filePath) {

        List<Problem> validProblems = new ArrayList<>();
        List<InvalidRecord> invalidRecords = new ArrayList<>();


        try (
                FileReader fileReader = new FileReader(filePath);
                CSVParser parser = CSVFormat.DEFAULT
                        .builder()
                        .setHeader()
                        .setSkipHeaderRecord(true)
                        .build()
                        .parse(fileReader)
        ) {

            int rowNumber = 1;

            List<String> requiredHeaders = List.of(
                    "title",
                    "category",
                    "difficulty",
                    "status",
                    "timeSpentMinutes"
            );

            for (String header : requiredHeaders) {
                if (!parser.getHeaderMap().containsKey(header)) {
                    return new ValidationResult(validProblems, invalidRecords);
                }
            }

            for (CSVRecord record : parser) {
                rowNumber++;

                String title = record.get("title").trim();
                String category = record.get("category").trim();
                String difficulty = record.get("difficulty").trim();
                String status = record.get("status").trim();
                String timeSpentValue = record.get("timeSpentMinutes").trim();

                if (title.isBlank()) {
                    invalidRecords.add(
                            new InvalidRecord(rowNumber, "Missing title")
                    );
                    continue;
                }

                if (category.isBlank()) {
                    invalidRecords.add(
                            new InvalidRecord(rowNumber, "Missing category")
                    );
                    continue;
                }

                if (!status.equalsIgnoreCase("done")
                        && !status.equalsIgnoreCase("completed")
                        && !status.equalsIgnoreCase("pending")) {

                    invalidRecords.add(
                            new InvalidRecord(
                                    rowNumber,
                                    "Invalid status"
                            )
                    );

                    continue;
                }

                int timeSpentMinutes;

                try {
                    timeSpentMinutes = Integer.parseInt(timeSpentValue);
                } catch (NumberFormatException e) {
                    invalidRecords.add(
                            new InvalidRecord(rowNumber, "Invalid time")
                    );
                    continue;
                }

                if (timeSpentMinutes < 0) {
                    invalidRecords.add(
                            new InvalidRecord(rowNumber, "Invalid time")
                    );
                    continue;
                }

                if (status.equalsIgnoreCase("done")) {
                    status = "completed";
                }

                Problem problem = new Problem(
                        title,
                        category,
                        difficulty,
                        status,
                        timeSpentMinutes
                );

                validProblems.add(problem);
            }

        } catch (IOException e) {
            System.out.println("Error reading CSV file");
            e.printStackTrace();
        }

        return new ValidationResult(validProblems, invalidRecords);
    }
}
