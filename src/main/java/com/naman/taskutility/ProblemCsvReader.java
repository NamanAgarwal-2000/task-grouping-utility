package com.naman.taskutility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProblemCsvReader {

    public ValidationResult readProblems(String filePath) {

        List<Problem> validProblems = new ArrayList<>();
        List<InvalidRecord> invalidRecords = new ArrayList<>();


        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line;

            // skip header row
            String header = reader.readLine();

            if (header == null) {
                return new ValidationResult(validProblems, invalidRecords);
            }

            int rowNumber = 1;

            while ((line = reader.readLine()) != null) {

                rowNumber++;

                String[] data = line.split(",");

                if (data.length != 5) {
                    invalidRecords.add(
                            new InvalidRecord(rowNumber, "Malformed CSV row")
                    );
                    continue;
                }

                String title = data[0].trim();

                if (title.isBlank()) {
                    invalidRecords.add(
                            new InvalidRecord(rowNumber, "Missing title")
                    );
                    continue;
                }
                String category = data[1].trim();
                if (category.isBlank()) {
                    invalidRecords.add(
                            new InvalidRecord(rowNumber, "Missing category")
                    );
                    continue;
                }
                String difficulty = data[2].trim();
                String status = data[3].trim();

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
                    timeSpentMinutes = Integer.parseInt(data[4].trim());
                } catch (NumberFormatException e) {
                    invalidRecords.add(
                            new InvalidRecord(rowNumber, "Invalid time")
                    );
                    continue;
                }
                if (status.equalsIgnoreCase("done")) {
                    status = "completed";
                }
                if (status.equalsIgnoreCase("completed")) {
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