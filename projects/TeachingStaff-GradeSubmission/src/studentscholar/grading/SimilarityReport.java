package studentscholar.grading;

/** Result returned by the external similarity-checking service. */
public final class SimilarityReport {
    private final String reportId;
    private final double similarityPercentage;
    private final String summary;

    public SimilarityReport(String reportId, double similarityPercentage, String summary) {
        this.reportId = reportId;
        this.similarityPercentage = similarityPercentage;
        this.summary = summary;
    }

    public String getReportId() {
        return reportId;
    }

    public double getSimilarityPercentage() {
        return similarityPercentage;
    }

    public String getSummary() {
        return summary;
    }
}
