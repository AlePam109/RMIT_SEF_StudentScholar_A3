package com.studentScholar.assessment;

import com.studentScholar.course.Course;
import com.studentScholar.grading.Rubric;
import com.studentScholar.observer.EventBus;
import com.studentScholar.persistence.Database;
import com.studentScholar.persistence.FileStorage;
import com.studentScholar.users.Student;

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
        return assessment.getTitle() + " (due " + assessment.submissionDeadline + ")";
    }

    // Rohan Chaudhari
    public boolean isSubmissionOpen(LocalDateTime dateTime) {
        return submissionDeadline != null && !dateTime.isAfter(submissionDeadline);
    }

    // Rohan Chaudhari
    public Submission submitAssessment(Student student, String fileName) {
        boolean isOpen = isSubmissionOpen(LocalDateTime.now());
        if (!isOpen) {
            ExtensionRequest request = null;
            String status = null;
            for (ExtensionRequest candidate : extensionRequests) {
                status = candidate.getStatus(student);
                if (status != null) {
                    request = candidate;
                    break;
                }
            }
            if (request == null || !"APPROVED".equals(status)) {
                System.out.println("Submission rejected: outside submission period for " + assessmentId);
                return null;
            }
            updateSubmissionDeadline(student, request.getExtendedDeadline());
        }
        requireCollaborators();

        Submission submission = new Submission(
                assessmentId + "-SUB-" + (submissions.size() + 1),
                student, fileName, database, fileStorage, eventBus);
        submission.setAssessment(this);
        submissions.add(submission);
        submission.submit(student);
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
        System.out.println("Deadline for " + student.getUserId() + " extended to " + extendedDeadline);
    }

    // Rohan Chaudhari
    private void requireCollaborators() {
        if (fileStorage == null || database == null || eventBus == null) {
            throw new IllegalStateException("FileStorage, Database and EventBus must be provided");
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
