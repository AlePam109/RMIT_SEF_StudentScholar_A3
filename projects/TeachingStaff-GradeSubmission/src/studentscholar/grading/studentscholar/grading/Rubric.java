package studentscholar.grading;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/** Defines the criteria and validates criterion marks. */
public final class Rubric {
    private final String rubricId;
    private final List<RubricCriterion> criteria;

    public Rubric(String rubricId, List<RubricCriterion> criteria) {
        if (criteria == null || criteria.isEmpty()) {
            throw new IllegalArgumentException("A rubric requires at least one criterion");
        }
        this.rubricId = requireText(rubricId, "rubricId");
        Set<String> criterionIds = new LinkedHashSet<String>();
        for (RubricCriterion criterion : criteria) {
            if (criterion == null) {
                throw new IllegalArgumentException("criteria must not contain null values");
            }
            if (!criterionIds.add(criterion.getCriterionId())) {
                throw new IllegalArgumentException(
                        "Duplicate rubric criterion: " + criterion.getCriterionId());
            }
        }
        this.criteria = new ArrayList<RubricCriterion>(criteria);
    }

    public String getRubricId() {
        return rubricId;
    }

    public List<RubricCriterion> getCriteria() {
        return Collections.unmodifiableList(criteria);
    }

    public RubricCriterion findCriterion(String criterionId) {
        for (RubricCriterion criterion : criteria) {
            if (criterion.getCriterionId().equals(criterionId)) {
                return criterion;
            }
        }
        throw new IllegalArgumentException("Unknown criterion: " + criterionId);
    }

    public MarkValidationResult validateMark(String criterionId, double mark) {
        RubricCriterion criterion = findCriterion(criterionId);
        if (!criterion.isWithinRange(mark)) {
            return MarkValidationResult.INVALID;
        }
        if (!criterion.isStandardMark(mark)) {
            return MarkValidationResult.CUSTOM_REQUIRES_JUSTIFICATION;
        }
        return MarkValidationResult.VALID_STANDARD;
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
