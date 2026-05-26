package com.naman.taskutility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProblemJsonReader {

    public List<Problem> readProblems(String filePath) {

        ObjectMapper objectMapper = new ObjectMapper();

        try {

            return objectMapper.readValue(
                    new File(filePath),
                    new TypeReference<List<Problem>>() {}
            );

        } catch (IOException e) {

            System.out.println("Error reading JSON file");
            e.printStackTrace();

            return new ArrayList<>();
        }
    }
}
