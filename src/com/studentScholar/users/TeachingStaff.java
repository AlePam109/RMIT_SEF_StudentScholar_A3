package com.studentScholar.users;

import com.studentScholar.assessment.Submission;
import com.studentScholar.consultation.ConsultationSlot;
import com.studentScholar.course.Course;
import com.studentScholar.grading.GradeRecord;

import java.util.ArrayList;
import java.util.List;

public class TeachingStaff extends User {
    // Rohan Chaudhari
    private List<Course> courses = new ArrayList<>();
    private List<ConsultationSlot> consultationSlots = new ArrayList<>();

    // Alec Pham
    public TeachingStaff(String staffId, String displayName) {
        super(requireText(staffId, "staffId"), requireText(displayName, "displayName"));
    }

    // Alec Pham
    public String getStaffId() {
        return getUserId();
    }

    // Alec Pham
    public String getDisplayName() {
        return getName();
    }

    // Rohan Chaudhari
    public GradeRecord gradeAssessment(Submission submission) {
        return null;
    }

    // Alec Pham
    private static String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
