package studentscholar.grading;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** An assessment, its marking rubric, and received submissions. */
public final class Assessment {
    private final String assessmentId;
    private final String title;
    private final Rubric rubric;
    private final List<Submission> submissions = new ArrayList<Submission>();

    public Assessment(String assessmentId, String title, Rubric rubric) {
        this.assessmentId = requireText(assessmentId, "assessmentId");
        this.title = requireText(title, "title");
        if (rubric == null) {
            throw new IllegalArgumentException("rubric must not be null");
        }
        this.rubric = rubric;
    }

    public String getAssessmentId() {
        return assessmentId;
    }

    public String getTitle() {
        return title;
    }

    public Rubric getRubric() {
        return rubric;
    }

    public void addSubmission(Submission submission) {
        if (submission == null) {
            throw new IllegalArgumentException("submission must not be null");
        }
        for (Submission existing : submissions) {
            if (existing.getSubmissionId().equals(submission.getSubmissionId())) {
                throw new IllegalArgumentException(
                        "Duplicate submission: " + submission.getSubmissionId());
            }
        }
        submissions.add(submission);
    }

    public List<Submission> listSubmissions() {
        return Collections.unmodifiableList(submissions);
    }

    public Submission findSubmission(String submissionId) {
        for (Submission submission : submissions) {
            if (submission.getSubmissionId().equals(submissionId)) {
                return submission;
            }
        }
        throw new IllegalArgumentException("Unknown submission: " + submissionId);
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
