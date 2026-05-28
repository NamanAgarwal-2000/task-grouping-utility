package com.naman.taskutility;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;


public class ReportJsonExporter {

    public void exportReport(ExportResult report, String outputPath){

        try {

            ObjectMapper mapper = new ObjectMapper();

            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(outputPath), report);

        } catch (IOException e) {

            System.out.println("Failed to export report");
        }
    }
}