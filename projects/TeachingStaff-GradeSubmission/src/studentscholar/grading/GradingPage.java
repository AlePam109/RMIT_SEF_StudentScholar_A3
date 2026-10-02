package studentscholar.grading;

import java.util.List;

/** Boundary class responsible for staff input and presentation output. */
public final class GradingPage {
    private GradingController controller;

    public void bindController(GradingController controller) {
        this.controller = controller;
    }

    public void openAssignedCourse(String courseId) {
        requireController().openGrading(courseId);
    }

    public void selectAssessment(String assessmentId) {
        requireController().selectAssessment(assessmentId);
    }

    public void selectSubmission(String submissionId) {
        requireController().selectSubmission(submissionId);
    }

    public void requestSimilarityCheck() {
        requireController().checkSimilarity();
    }

    public void submitCriterionMark(
            String criterionId,
            double mark,
            String comment) {
        requireController().submitCriterionMark(criterionId, mark, comment);
    }

    public void submitJustification(String justification) {
        requireController().submitJustification(justification);
    }

    public void saveDraft(String feedback) {
        requireController().saveDraft(feedback);
    }

    public void displayAccessError() {
        System.out.println("Access denied: staff member is not assigned to the course.");
    }

    public void displayAssessments(List<Assessment> assessments) {
        System.out.println("Assessments available: " + assessments.size());
        for (Assessment assessment : assessments) {
            System.out.println("  - " + assessment.getAssessmentId() + ": " + assessment.getTitle());
        }
    }

    public void displaySubmissions(List<Submission> submissions) {
        System.out.println("Submissions available: " + submissions.size());
        for (Submission submission : submissions) {
            System.out.println("  - " + submission.getSubmissionId()
                    + " for student " + submission.getStudentId());
        }
    }

    public void displayGradingWorkspace(
            String content,
            List<RubricCriterion> criteria) {
        System.out.println("Grading workspace loaded.");
        System.out.println("Submission content: " + content);
        System.out.println("Rubric criteria: " + criteria.size());
    }

    public void displaySimilarityReport(SimilarityReport report) {
        System.out.println("Similarity report " + report.getReportId()
                + ": " + report.getSimilarityPercentage() + "%");
    }

    public void displaySimilarityUnavailable(String reason) {
        System.out.println("Similarity report unavailable: " + reason);
    }

    public void displayValidationError(String permittedRange) {
        System.out.println("Invalid mark. Permitted range: " + permittedRange);
    }

    public void showJustificationPrompt() {
        System.out.println("A custom mark requires a justification.");
    }

    public void criterionAccepted(String criterionId) {
        System.out.println("Criterion mark recorded: " + criterionId);
    }

    public void displayDraftConfirmation(double totalMark) {
        System.out.println("Draft grade saved successfully. Total mark: " + totalMark);
    }

    private GradingController requireController() {
        if (controller == null) {
            throw new IllegalStateException("GradingPage has not been bound to a controller");
        }
        return controller;
    }
}
