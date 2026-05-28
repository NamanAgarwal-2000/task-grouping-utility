package com.naman.taskutility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProblemJsonReader {

    public ValidationResult readProblems(String filePath) {

        ObjectMapper objectMapper = new ObjectMapper();

        List<Problem> validProblems = new ArrayList<>();
        List<InvalidRecord> invalidRecords = new ArrayList<>();

        try {

            List<Map<String, Object>> rawProblems =
                    objectMapper.readValue(
                            new File(filePath),
                            new TypeReference<List<Map<String, Object>>>() {}
                    );

            for (int i = 0; i < rawProblems.size(); i++) {

                Map<String, Object> data = rawProblems.get(i);

                String title = (String) data.get("title");
                String category = (String) data.get("category");
                String difficulty = (String) data.get("difficulty");
                String status = (String) data.get("status");

                Integer timeSpentMinutes = null;

                try {
                    timeSpentMinutes =
                            (Integer) data.get("timeSpentMinutes");
                } catch (Exception e) {

                    invalidRecords.add(
                            new InvalidRecord(i, "Invalid time")
                    );

                    continue;
                }

                if (title == null || title.trim().isEmpty()) {

                    invalidRecords.add(
                            new InvalidRecord(i, "Missing title")
                    );

                    continue;
                }

                if (category == null || category.trim().isEmpty()) {

                    invalidRecords.add(
                            new InvalidRecord(i, "Missing category")
                    );

                    continue;
                }

                if (status == null
                        || (!status.equalsIgnoreCase("done")
                        && !status.equalsIgnoreCase("pending"))) {

                    invalidRecords.add(
                            new InvalidRecord(i, "Invalid status")
                    );

                    continue;
                }

                if (timeSpentMinutes == null
                        || timeSpentMinutes < 0) {

                    invalidRecords.add(
                            new InvalidRecord(i, "Invalid time")
                    );

                    continue;
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

            return new ValidationResult(
                    validProblems,
                    invalidRecords
            );

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to read JSON file",
                    e
            );
        }
    }
}