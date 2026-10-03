package studentscholar.grading;

import java.util.Arrays;
import java.util.LinkedHashSet;

/**
 * Dependency-free workflow checks for the assessed grading behaviour.
 * Run with assertions enabled: {@code java -ea studentscholar.grading.GradingWorkflowChecks}.
 */
public final class GradingWorkflowChecks {
    private GradingWorkflowChecks() {
    }

    public static void main(String[] args) {
        verifiesSuccessfulStandardAndCustomMarkFlow();
        rejectsActionsWithoutCourseAuthorisation();
        rejectsIncompleteDrafts();
        rejectsOutOfRangeMarks();
        rejectsBlankCustomMarkJustification();
        protectsPendingCustomMarks();
        preventsReplacingTheOwnedGradeRecord();
        handlesUnavailableSimilarityService();
        System.out.println("All grading workflow checks passed.");
    }

    private static void verifiesSuccessfulStandardAndCustomMarkFlow() {
        Fixture fixture = new Fixture(true, true);
        fixture.openWorkspace();

        fixture.page.submitCriterionMark("CRIT-1", 7.5, "Strong analysis.");
        fixture.page.submitCriterionMark("CRIT-2", 8.0, "Clear presentation.");
        fixture.page.submitJustification("The work falls between two descriptors.");
        fixture.page.saveDraft("Sound technical reasoning.");

        assert fixture.grade.getStatus() == GradeStatus.DRAFT;
        assert fixture.grade.getCriterionMarks().size() == 2;
        assert fixture.grade.getTotalMark() == 15.5;
    }

    private static void rejectsActionsWithoutCourseAuthorisation() {
        Fixture fixture = new Fixture(false, true);
        fixture.page.openAssignedCourse("COURSE-01");
        expectIllegalState(
                () -> fixture.page.selectAssessment("ASSESSMENT-01"),
                "unauthorised assessment selection");
    }

    private static void rejectsIncompleteDrafts() {
        Fixture fixture = new Fixture(true, true);
        fixture.openWorkspace();
        fixture.page.submitCriterionMark("CRIT-1", 7.5, "Strong analysis.");

        expectIllegalState(
                () -> fixture.page.saveDraft("Only one criterion has been marked."),
                "incomplete draft");
    }

    private static void protectsPendingCustomMarks() {
        Fixture fixture = new Fixture(true, true);
        fixture.openWorkspace();
        fixture.page.submitCriterionMark("CRIT-1", 8.0, "Custom mark.");

        expectIllegalState(
                () -> fixture.page.submitCriterionMark("CRIT-2", 7.5, "Next mark."),
                "overwriting a pending custom mark");
    }

    private static void rejectsOutOfRangeMarks() {
        Fixture fixture = new Fixture(true, true);
        fixture.openWorkspace();
        fixture.page.submitCriterionMark("CRIT-1", 11.0, "Outside the range.");
        assert !fixture.grade.containsMarkFor("CRIT-1");
    }

    private static void rejectsBlankCustomMarkJustification() {
        Fixture fixture = new Fixture(true, true);
        fixture.openWorkspace();
        fixture.page.submitCriterionMark("CRIT-1", 8.0, "Custom mark.");

        expectIllegalArgument(
                () -> fixture.page.submitJustification("   "),
                "blank custom-mark justification");
    }

    private static void preventsReplacingTheOwnedGradeRecord() {
        Fixture fixture = new Fixture(true, true);
        expectIllegalState(
                () -> fixture.submission.attachDraftGrade(new GradeRecord("GRADE-02")),
                "replacing a submission's GradeRecord");
    }

    private static void handlesUnavailableSimilarityService() {
        Fixture fixture = new Fixture(true, false);
        fixture.openWorkspace();
        fixture.page.requestSimilarityCheck();
        assert !fixture.submission.getSimilarityReport().isPresent();
    }

    private static void expectIllegalState(Runnable operation, String scenario) {
        try {
            operation.run();
        } catch (IllegalStateException expected) {
            return;
        }
        throw new AssertionError("Expected IllegalStateException for " + scenario);
    }

    private static void expectIllegalArgument(Runnable operation, String scenario) {
        try {
            operation.run();
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new AssertionError("Expected IllegalArgumentException for " + scenario);
    }

    private static final class Fixture {
        private final TeachingStaff staff =
                new TeachingStaff("STAFF-101", "Teaching Staff Test");
        private final GradeRecord grade = new GradeRecord("GRADE-01");
        private final Submission submission = new Submission(
                "SUBMISSION-01",
                "STUDENT-001",
                "Submitted assessment content");
        private final GradingPage page = new GradingPage();

        private Fixture(boolean assignStaff, boolean similarityAvailable) {
            RubricCriterion analysis = criterion("CRIT-1", "Analysis");
            RubricCriterion communication = criterion("CRIT-2", "Communication");
            Rubric rubric = new Rubric(
                    "RUBRIC-01",
                    Arrays.asList(analysis, communication));

            Assessment assessment = new Assessment(
                    "ASSESSMENT-01",
                    "Software Design Report",
                    rubric);
            submission.attachDraftGrade(grade);
            assessment.addSubmission(submission);

            Course course = new Course(
                    "COURSE-01",
                    "Software Engineering Fundamentals");
            if (assignStaff) {
                course.assignStaff(staff);
            }
            course.addAssessment(assessment);

            GradingController controller = new GradingController(
                    staff,
                    page,
                    course,
                    new SimilarityCheckService(similarityAvailable));
            page.bindController(controller);
        }

        private void openWorkspace() {
            page.openAssignedCourse("COURSE-01");
            page.selectAssessment("ASSESSMENT-01");
            page.selectSubmission("SUBMISSION-01");
        }

        private static RubricCriterion criterion(String id, String description) {
            return new RubricCriterion(
                    id,
                    description,
                    10.0,
                    new LinkedHashSet<Double>(
                            Arrays.asList(0.0, 2.5, 5.0, 7.5, 10.0)));
        }
    }
}
