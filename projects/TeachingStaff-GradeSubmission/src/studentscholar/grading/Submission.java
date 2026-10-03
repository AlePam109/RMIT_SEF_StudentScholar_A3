package studentscholar.grading;

import java.util.Optional;

/** A student's submitted assessment content and its grading associations. */
public final class Submission {
    private final String submissionId;
    private final String studentId;
    private final String supportedContent;
    private SimilarityReport similarityReport;
    private GradeRecord gradeRecord;

    public Submission(String submissionId, String studentId, String supportedContent) {
        this.submissionId = requireText(submissionId, "submissionId");
        this.studentId = requireText(studentId, "studentId");
        this.supportedContent = requireText(supportedContent, "supportedContent");
    }

    public String getSubmissionId() {
        return submissionId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getSupportedContent() {
        return supportedContent;
    }

    public void linkSimilarityReport(SimilarityReport report) {
        if (report == null) {
            throw new IllegalArgumentException("report must not be null");
        }
        this.similarityReport = report;
    }

    public Optional<SimilarityReport> getSimilarityReport() {
        return Optional.ofNullable(similarityReport);
    }

    /**
     * Fixture/setup operation outside the sequence diagram's grading interaction.
     * It establishes the existing 0..1 Submission-to-GradeRecord composition.
     */
    public void attachDraftGrade(GradeRecord draftGrade) {
        if (draftGrade == null) {
            throw new IllegalArgumentException("draftGrade must not be null");
        }
        if (gradeRecord != null) {
            throw new IllegalStateException(
                    "A submission cannot own more than one GradeRecord");
        }
        this.gradeRecord = draftGrade;
    }

    public Optional<GradeRecord> getGradeRecord() {
        return Optional.ofNullable(gradeRecord);
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
