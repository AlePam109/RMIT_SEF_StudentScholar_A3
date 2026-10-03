package studentscholar.grading;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/** Course aggregate used to verify staff assignment and locate assessments. */
public final class Course {
    private final String courseId;
    private final String title;
    private final Set<String> assignedStaffIds = new LinkedHashSet<String>();
    private final List<Assessment> assessments = new ArrayList<Assessment>();

    public Course(String courseId, String title) {
        this.courseId = requireText(courseId, "courseId");
        this.title = requireText(title, "title");
    }

    public String getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public void assignStaff(TeachingStaff staff) {
        if (staff == null) {
            throw new IllegalArgumentException("staff must not be null");
        }
        assignedStaffIds.add(staff.getStaffId());
    }

    public boolean verifyAssignment(String staffId, String requestedCourseId) {
        return courseId.equals(requestedCourseId) && assignedStaffIds.contains(staffId);
    }

    public void addAssessment(Assessment assessment) {
        if (assessment == null) {
            throw new IllegalArgumentException("assessment must not be null");
        }
        for (Assessment existing : assessments) {
            if (existing.getAssessmentId().equals(assessment.getAssessmentId())) {
                throw new IllegalArgumentException(
                        "Duplicate assessment: " + assessment.getAssessmentId());
            }
        }
        assessments.add(assessment);
    }

    public List<Assessment> getAssessments() {
        return Collections.unmodifiableList(assessments);
    }

    public Assessment findAssessment(String assessmentId) {
        for (Assessment assessment : assessments) {
            if (assessment.getAssessmentId().equals(assessmentId)) {
                return assessment;
            }
        }
        throw new IllegalArgumentException("Unknown assessment: " + assessmentId);
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
