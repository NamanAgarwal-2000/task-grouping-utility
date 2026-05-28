package com.naman.taskutility;

import java.util.List;

public class ExportResult {

    private ReportSummary reportSummary;
    private List<InvalidRecord> invalidRecords;

    public ExportResult(ReportSummary reportSummary,
                        List<InvalidRecord> invalidRecords) {

        this.reportSummary = reportSummary;
        this.invalidRecords = invalidRecords;
    }

    public ReportSummary getReportSummary() {
        return reportSummary;
    }

    public List<InvalidRecord> getInvalidRecords() {
        return invalidRecords;
    }
}