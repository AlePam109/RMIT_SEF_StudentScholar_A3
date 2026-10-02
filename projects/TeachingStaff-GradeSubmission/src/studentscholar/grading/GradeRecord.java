package studentscholar.grading;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/** Draft or released grading information owned by a Submission. */
public final class GradeRecord {
    private final String gradeId;
    private final Map<String, CriterionMark> criterionMarks =
            new LinkedHashMap<String, CriterionMark>();
    private GradeStatus status = GradeStatus.DRAFT;
    private double totalMark;
    private String overallFeedback = "";

    public GradeRecord(String gradeId) {
        if (gradeId == null || gradeId.trim().isEmpty()) {
            throw new IllegalArgumentException("gradeId must not be blank");
        }
        this.gradeId = gradeId;
    }

    public String getGradeId() {
        return gradeId;
    }

    public GradeStatus getStatus() {
        return status;
    }

    public Collection<CriterionMark> getCriterionMarks() {
        return Collections.unmodifiableCollection(criterionMarks.values());
    }

    public double getTotalMark() {
        return totalMark;
    }

    public String getOverallFeedback() {
        return overallFeedback;
    }

    public void recordCriterionMark(CriterionMark mark) {
        if (mark == null) {
            throw new IllegalArgumentException("mark must not be null");
        }
        criterionMarks.put(mark.getCriterionId(), mark);
    }

    public double calculateTotal() {
        double calculatedTotal = 0;
        for (CriterionMark mark : criterionMarks.values()) {
            calculatedTotal += mark.getAwardedMark();
        }
        return calculatedTotal;
    }

    public void saveDraft(double totalMark, String feedback) {
        this.totalMark = totalMark;
        this.overallFeedback = feedback == null ? "" : feedback.trim();
        this.status = GradeStatus.DRAFT;
    }
}
