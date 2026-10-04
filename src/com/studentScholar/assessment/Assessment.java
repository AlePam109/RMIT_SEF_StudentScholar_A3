package com.studentScholar.assessment;

import com.studentScholar.course.Course;
import com.studentScholar.grading.Rubric;
import com.studentScholar.observer.EventBus;
import com.studentScholar.persistence.Database;
import com.studentScholar.persistence.FileStorage;
import com.studentScholar.users.Student;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Assessment {
    private String assessmentId;                  // Rohan Chaudhari
    private String title;                         // Alec Pham
    private LocalDateTime submissionDeadline;     // Rohan Chaudhari

    private Course course;                        // Rohan Chaudhari
    private Rubric rubric;                        // Alec Pham
    private List<Submission> submissions = new ArrayList<>();             // Rohan Chaudhari
    private List<ExtensionRequest> extensionRequests = new ArrayList<>(); // Rohan Chaudhari
    private EventBus eventBus;                    // Rohan Chaudhari
    private FileStorage fileStorage;              // Rohan Chaudhari
    private Database database;                    // Rohan Chaudhari

    // Alec Pham
    public Assessment(String assessmentId, String title, Rubric rubric) {
        this(assessmentId, title, rubric, null, null, null, null);
    }

    // Alec Pham
    public Assessment(String assessmentId, String title, Rubric rubric, EventBus eventBus) {
        this(assessmentId, title, rubric, eventBus, null, null, null);
    }

    // Rohan Chaudhari
    public Assessment(String assessmentId, String title, Rubric rubric, EventBus eventBus,
                      FileStorage fileStorage, Database database, LocalDateTime submissionDeadline) {
        this.assessmentId = requireText(assessmentId, "assessmentId");
        this.title = requireText(title, "title");
        if (rubric == null) {
            throw new IllegalArgumentException("rubric must not be null");
        }
        this.rubric = rubric;
        this.eventBus = eventBus;
        this.fileStorage = fileStorage;
        this.database = database;
        this.submissionDeadline = submissionDeadline;
    }

    // Alec Pham
    public String getAssessmentId() {
        return assessmentId;
    }

    // Alec Pham
    public String getTitle() {
        return title;
    }

    // Alec Pham
    public Rubric getRubric() {
        return rubric;
    }

    // Rohan Chaudhari
    public String selectAssessment(Assessment assessment) {
        return assessment.getAssessmentId();
    }

    // Rohan Chaudhari
    public boolean isSubmissionOpen(LocalDateTime dateTime) {
        return submissionDeadline != null && !dateTime.isAfter(submissionDeadline);
    }

    // Rohan Chaudhari
    public Submission submitAssessment(Student student, String fileName) {
        LocalDateTime now = LocalDateTime.now();
        if (!isSubmissionOpen(now) && !hasApprovedExtension(student, now)) {
            System.out.println("Submission period closed for " + assessmentId);
            return null;
        }
        requireCollaborators();

        fileStorage.save(new File(fileName));
        Submission submission = new Submission(
                assessmentId + "-SUB-" + (submissions.size() + 1),
                student,
                fileName,
                fileName);
        submission.setAssessment(this);
        submission.recordSubmissionDateTime();
        submissions.add(submission);
        database.save(submission);
        submission.generateConfirmation();
        return submission;
    }

    // Rohan Chaudhari
    public ExtensionRequest requestExtension(Student student, String reason) {
        ExtensionRequest request = new ExtensionRequest(eventBus, student, this, reason);
        extensionRequests.add(request);
        request.submit();
        return request;
    }

    // Rohan Chaudhari
    public void updateSubmissionDeadline(Student student, LocalDateTime extendedDeadline) {
        if (extendedDeadline == null) {
            throw new IllegalArgumentException("extendedDeadline must not be null");
        }
        for (ExtensionRequest request : extensionRequests) {
            if (!"NOT_FOUND".equals(request.getStatus(student))) {
                request.approve(extendedDeadline);
                System.out.println("Deadline for " + student.getUserId()
                        + " extended to " + extendedDeadline);
                return;
            }
        }
        throw new IllegalStateException("No extension request found for " + student.getUserId());
    }

    // Rohan Chaudhari
    private boolean hasApprovedExtension(Student student, LocalDateTime dateTime) {
        for (ExtensionRequest request : extensionRequests) {
            if (request.isApprovedFor(student, dateTime)) {
                return true;
            }
        }
        return false;
    }

    // Rohan Chaudhari
    private void requireCollaborators() {
        if (fileStorage == null || database == null) {
            throw new IllegalStateException("FileStorage and Database must be provided");
        }
    }

    // Alec Pham
    public void addSubmission(Submission submission) {
        submissions.add(submission);
    }

    // Alec Pham
    public List<Submission> listSubmissions() {
        return Collections.unmodifiableList(submissions);
    }

    // Alec Pham
    public Submission findSubmission(String submissionId) {
        for (Submission submission : submissions) {
            if (submission.getSubmissionId().equals(submissionId)) {
                return submission;
            }
        }
        throw new IllegalArgumentException("Unknown submission: " + submissionId);
    }

    // Alec Pham
    private static String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
