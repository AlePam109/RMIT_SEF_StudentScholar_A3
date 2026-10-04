package com.studentScholar.assessment;

import com.studentScholar.grading.GradeRecord;
import com.studentScholar.grading.SimilarityReport;
import com.studentScholar.users.Student;

import java.time.LocalDateTime;
import java.util.Optional;

public class Submission {
    private String submissionId;                 // Rohan Chaudhari
    private String fileName;                     // Rohan Chaudhari
    private LocalDateTime submittedAt;           // Rohan Chaudhari
    private String supportedContent;             // Alec Pham

    private Student student;                     // Rohan Chaudhari
    private Assessment assessment;               // Rohan Chaudhari
    private GradeRecord gradeRecord;             // Alec Pham
    private SimilarityReport similarityReport;   // Alec Pham

    // Alec Pham
    public Submission(String submissionId, Student student, String fileName, String supportedContent) {
        this.submissionId = requireText(submissionId, "submissionId");
        if (student == null) {
            throw new IllegalArgumentException("student must not be null");
        }
        this.student = student;
        this.fileName = fileName;
        this.supportedContent = requireText(supportedContent, "supportedContent");
    }

    // Alec Pham
    public String getSubmissionId() {
        return submissionId;
    }

    // Rohan Chaudhari
    public String getFileName() {
        return fileName;
    }

    // Rohan Chaudhari
    public Student getStudent() {
        return student;
    }

    // Alec Pham
    public String getStudentId() {
        return student.getUserId();
    }

    // Alec Pham
    public String getSupportedContent() {
        return supportedContent;
    }

    // Rohan Chaudhari
    void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    // Rohan Chaudhari
    public Assessment getAssessment() {
        return assessment;
    }

    // Rohan Chaudhari
    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    // Rohan Chaudhari
    public void submit(Student student) {
    }

    // Rohan Chaudhari
    public void recordSubmissionDateTime() {
        this.submittedAt = LocalDateTime.now();
    }

    // Rohan Chaudhari
    public void generateConfirmation() {
        System.out.println("Submission confirmed: " + submissionId + " (" + fileName + ") at " + submittedAt);
    }

    // Alec Pham
    public void linkSimilarityReport(SimilarityReport report) {
        if (report == null) {
            throw new IllegalArgumentException("report must not be null");
        }
        this.similarityReport = report;
    }

    // Alec Pham
    public Optional<SimilarityReport> getSimilarityReport() {
        return Optional.ofNullable(similarityReport);
    }

    // Alec Pham
    /**
     * Fixture/setup operation outside the grading sequence interaction.
     * It establishes the 0..1 Submission-to-GradeRecord composition.
     */
    public void attachDraftGrade(GradeRecord draftGrade) {
        if (draftGrade == null) {
            throw new IllegalArgumentException("draftGrade must not be null");
        }
        this.gradeRecord = draftGrade;
    }

    // Alec Pham
    public Optional<GradeRecord> getGradeRecord() {
        return Optional.ofNullable(gradeRecord);
    }

    // Alec Pham
    private static String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
