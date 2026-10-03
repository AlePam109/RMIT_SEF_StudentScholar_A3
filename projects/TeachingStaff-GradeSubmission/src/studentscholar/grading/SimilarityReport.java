package studentscholar.grading;

/** Result returned by the external similarity-checking service. */
public final class SimilarityReport {
    private final String reportId;
    private final double similarityPercentage;
    private final String summary;

    public SimilarityReport(String reportId, double similarityPercentage, String summary) {
        if (reportId == null || reportId.trim().isEmpty()) {
            throw new IllegalArgumentException("reportId must not be blank");
        }
        if (!Double.isFinite(similarityPercentage)
                || similarityPercentage < 0
                || similarityPercentage > 100) {
            throw new IllegalArgumentException(
                    "similarityPercentage must be between 0 and 100");
        }
        this.reportId = reportId;
        this.similarityPercentage = similarityPercentage;
        this.summary = summary == null ? "" : summary.trim();
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
