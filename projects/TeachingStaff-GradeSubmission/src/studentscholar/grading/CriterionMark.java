package studentscholar.grading;

/** A mark and feedback recorded against one rubric criterion. */
public final class CriterionMark {
    private final String criterionId;
    private final double awardedMark;
    private final String comment;
    private final String justification;

    public CriterionMark(
            String criterionId,
            double awardedMark,
            String comment,
            String justification) {
        this.criterionId = requireText(criterionId, "criterionId");
        this.awardedMark = awardedMark;
        this.comment = comment == null ? "" : comment.trim();
        this.justification = justification == null ? "" : justification.trim();
    }

    public String getCriterionId() {
        return criterionId;
    }

    public double getAwardedMark() {
        return awardedMark;
    }

    public String getComment() {
        return comment;
    }

    public String getJustification() {
        return justification;
    }

    public boolean hasJustification() {
        return !justification.isEmpty();
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
