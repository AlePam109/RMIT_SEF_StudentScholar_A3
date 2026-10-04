package com.studentScholar.course;

import com.studentScholar.assessment.Assessment;
import com.studentScholar.communication.Announcement;
import com.studentScholar.consultation.ConsultationSlot;
import com.studentScholar.users.TeachingStaff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Course {
    // Rohan Chaudhari
    private String courseCode;
    private String courseName;

    private List<Assessment> assessments = new ArrayList<>();
    private CourseShell courseShell;
    private List<TeachingStaff> teachingStaff = new ArrayList<>();
    private List<Enrolment> enrolments = new ArrayList<>();
    private List<ConsultationSlot> consultationSlots = new ArrayList<>();
    private List<ExternalTool> externalTools = new ArrayList<>();

    // Alec Pham
    public Course(String courseCode, String courseName) {
        this.courseCode = requireText(courseCode, "courseCode");
        this.courseName = requireText(courseName, "courseName");
    }

    // Alec Pham
    public String getCourseCode() {
        return courseCode;
    }

    // Alec Pham
    public String getCourseName() {
        return courseName;
    }

    // Alec Pham
    public void assignStaff(TeachingStaff staff) {
        if (staff != null && !teachingStaff.contains(staff)) {
            teachingStaff.add(staff);
        }
    }

    // Alec Pham
    public boolean verifyAssignment(String staffId, String requestedCourseId) {
        if (!courseCode.equals(requestedCourseId)) {
            return false;
        }
        for (TeachingStaff staff : teachingStaff) {
            if (staff.getStaffId().equals(staffId)) {
                return true;
            }
        }
        return false;
    }

    // Rohan Chaudhari
    public void addAssessment(Assessment assessment) {
        assessments.add(assessment);
    }

    // Alec Pham
    public List<Assessment> getAssessments() {
        return Collections.unmodifiableList(assessments);
    }

    // Alec Pham
    public Assessment findAssessment(String assessmentId) {
        for (Assessment assessment : assessments) {
            if (assessment.getAssessmentId().equals(assessmentId)) {
                return assessment;
            }
        }
        throw new IllegalArgumentException("Unknown assessment: " + assessmentId);
    }

    // Rohan Chaudhari
    public void addAnnouncement(Announcement announcement) {
        // Announcement is managed through the CourseShell
    }

    // Alec Pham
    private static String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
