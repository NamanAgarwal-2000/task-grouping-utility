package com.naman.taskutility;

import java.io.FileWriter;
import java.io.IOException;

public class ReportJsonExporter {

    public void exportReport(ReportSummary report, String outputPath) {

        try (FileWriter writer = new FileWriter(outputPath)) {

            writer.write("{\n");

            writer.write("  \"completedProblems\": " + report.getCompletedProblems() + ",\n");

            writer.write("  \"pendingProblems\": " + report.getPendingProblems() + ",\n");

            writer.write("  \"totalProblems\": " + report.getTotalProblems() + ",\n");

            writer.write("  \"totalTimeSpent\": \"" + report.getTotalTimeSpent() + "\"\n");

            writer.write("}");

        } catch (IOException e) {

            System.out.println("Failed to export report");
        }
    }
}