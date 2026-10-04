// Alec Pham
package com.studentScholar.grading;

import com.studentScholar.assessment.Assessment;
import com.studentScholar.assessment.Submission;
import com.studentScholar.course.Course;
import com.studentScholar.users.TeachingStaff;

/**
 * Control class coordinating the Grade Submission sequence.
 * It keeps workflow logic out of the boundary and entity classes.
 */
public final class GradingController {
    private final TeachingStaff staff;
    private final GradingPage page;
    private final Course course;
    private final SimilarityCheckService similarityService;

    private Assessment selectedAssessment;
    private Submission selectedSubmission;
    private Rubric activeRubric;
    private GradeRecord draftGrade;

    private String pendingCriterionId;
    private double pendingMark;
    private String pendingComment;

    public GradingController(
            TeachingStaff staff,
            GradingPage page,
            Course course,
            SimilarityCheckService similarityService) {
        this.staff = staff;
        this.page = page;
        this.course = course;
        this.similarityService = similarityService;
    }

    public void openGrading(String courseId) {
        if (!course.verifyAssignment(staff.getStaffId(), courseId)) {
            page.displayAccessError();
            return;
        }
        page.displayAssessments(course.getAssessments());
    }

    public void selectAssessment(String assessmentId) {
        selectedAssessment = course.findAssessment(assessmentId);
        page.displaySubmissions(selectedAssessment.listSubmissions());
    }

    public void selectSubmission(String submissionId) {
        requireAssessmentSelected();
        selectedSubmission = selectedAssessment.findSubmission(submissionId);
        activeRubric = selectedAssessment.getRubric();

        // The sequence diagram states that this draft was initialized beforehand.
        draftGrade = selectedSubmission.getGradeRecord().orElseThrow(
                () -> new IllegalStateException("No initialized draft GradeRecord"));

        // These independent reads correspond to the sequence diagram's par fragment.
        // They are sequential here because both collaborators are in-memory skeletons.
        page.displayGradingWorkspace(
                selectedSubmission.getSupportedContent(),
                activeRubric.getCriteria());
    }

    public void checkSimilarity() {
        requireSubmissionSelected();
        try {
            String content = selectedSubmission.getSupportedContent();
            SimilarityReport report = similarityService.analyse(content);
            selectedSubmission.linkSimilarityReport(report);
            page.displaySimilarityReport(report);
        } catch (SimilarityServiceException exception) {
            page.displaySimilarityUnavailable(exception.getMessage());
        }
    }

    public void submitCriterionMark(String criterionId, double mark, String comment) {
        requireSubmissionSelected();
        MarkValidationResult result = activeRubric.validateMark(criterionId, mark);

        if (result == MarkValidationResult.INVALID) {
            String range = activeRubric.findCriterion(criterionId).permittedRange();
            page.displayValidationError(range);
            return;
        }

        if (result == MarkValidationResult.CUSTOM_REQUIRES_JUSTIFICATION) {
            pendingCriterionId = criterionId;
            pendingMark = mark;
            pendingComment = comment;
            page.showJustificationPrompt();
            return;
        }

        recordCriterionMark(criterionId, mark, comment, "");
    }

    public void submitJustification(String justification) {
        if (pendingCriterionId == null) {
            throw new IllegalStateException("No custom mark is awaiting justification");
        }
        if (justification == null || justification.trim().isEmpty()) {
            throw new IllegalArgumentException("Custom-mark justification must not be blank");
        }

        recordCriterionMark(
                pendingCriterionId,
                pendingMark,
                pendingComment,
                justification);
        clearPendingMark();
    }

    public void saveDraft(String feedback) {
        requireSubmissionSelected();
        if (pendingCriterionId != null) {
            throw new IllegalStateException("Complete the pending custom mark first");
        }

        double totalMark = draftGrade.calculateTotal();
        draftGrade.saveDraft(totalMark, feedback);
        page.displayDraftConfirmation(totalMark);
    }

    private void recordCriterionMark(
            String criterionId,
            double mark,
            String comment,
            String justification) {
        draftGrade.recordCriterionMark(
                new CriterionMark(criterionId, mark, comment, justification));
        page.criterionAccepted(criterionId);
    }

    private void clearPendingMark() {
        pendingCriterionId = null;
        pendingMark = 0;
        pendingComment = null;
    }

    private void requireAssessmentSelected() {
        if (selectedAssessment == null) {
            throw new IllegalStateException("Select an assessment first");
        }
    }

    private void requireSubmissionSelected() {
        if (selectedSubmission == null || activeRubric == null || draftGrade == null) {
            throw new IllegalStateException("Select a submission first");
        }
    }
}
