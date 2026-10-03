package studentscholar.grading;

/** Represents the teaching staff actor participating in the grading workflow. */
public final class TeachingStaff {
    private final String staffId;
    private final String displayName;

    public TeachingStaff(String staffId, String displayName) {
        this.staffId = requireText(staffId, "staffId");
        this.displayName = requireText(displayName, "displayName");
    }

    public String getStaffId() {
        return staffId;
    }

    public String getDisplayName() {
        return displayName;
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
