// Alec Pham
package com.studentScholar.grading;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/** A single assessable criterion and its permitted standard marks. */
public final class RubricCriterion {
    private final String criterionId;
    private final String description;
    private final double maximumMark;
    private final Set<Double> standardMarks;

    public RubricCriterion(
            String criterionId,
            String description,
            double maximumMark,
            Set<Double> standardMarks) {
        if (maximumMark <= 0) {
            throw new IllegalArgumentException("maximumMark must be greater than zero");
        }
        this.criterionId = requireText(criterionId, "criterionId");
        this.description = requireText(description, "description");
        this.maximumMark = maximumMark;
        this.standardMarks = Collections.unmodifiableSet(
                new LinkedHashSet<Double>(standardMarks));
    }

    public String getCriterionId() {
        return criterionId;
    }

    public String getDescription() {
        return description;
    }

    public double getMaximumMark() {
        return maximumMark;
    }

    public boolean isWithinRange(double mark) {
        return mark >= 0 && mark <= maximumMark;
    }

    public boolean isStandardMark(double mark) {
        return standardMarks.contains(mark);
    }

    public String permittedRange() {
        return "0.0 to " + maximumMark;
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
