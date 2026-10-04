// Rohan Chaudhari
package com.studentScholar.users;

import com.studentScholar.assessment.Assessment;
import com.studentScholar.assessment.ExtensionRequest;
import com.studentScholar.assessment.Submission;
import com.studentScholar.consultation.Booking;
import com.studentScholar.course.Enrolment;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private String studentName;
    // Prabhuta
    private String email;
    private List<Enrolment> enrolments = new ArrayList<>();
    private List<Submission> submissions = new ArrayList<>();
    private List<ExtensionRequest> extensionRequests = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();

    public Student(String userId, String name) {
        super(userId, name);
    }

    public Submission submitAssessment(Assessment assessment, String fileName) {
        Submission submission = assessment.submitAssessment(this, fileName);
        if (submission != null) {
            submissions.add(submission);
        }
        return submission;
    }

    // Prabhuta
    public static Student findStudent(String email) {
        if (email == null || email.trim().isEmpty()) {
            return null;
        }
        // Skeleton lookup of an existing student.
        Student student = new Student(email, email);
        student.email = email;
        return student;
    }

    // Prabhuta
    public String getEmail() {
        return email;
    }
}
