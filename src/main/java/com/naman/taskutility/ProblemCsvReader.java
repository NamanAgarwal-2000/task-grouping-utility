package com.naman.taskutility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProblemCsvReader {

    public List<Problem> readProblems(String filePath) {

        List<Problem> problems = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line;

            // skip header row
            String header = reader.readLine();

            if (header == null) {
                return problems;
            }

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                if (data.length != 5) {
                    continue;
                }

                String title = data[0].trim();
                if (title.isBlank()) {
                    continue;
                }
                String category = data[1].trim();
                String difficulty = data[2].trim();
                String status = data[3].trim();

                int timeSpentMinutes;

                try {
                    timeSpentMinutes = Integer.parseInt(data[4].trim());
                } catch (NumberFormatException e) {
                   continue;
                }

                Problem problem = new Problem(
                        title,
                        category,
                        difficulty,
                        status,
                        timeSpentMinutes
                );

                problems.add(problem);
            }

        } catch (IOException e) {
            System.out.println("Error reading CSV file");
            e.printStackTrace();
        }

        return problems;
    }
}