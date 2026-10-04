// Rohan Chaudhari
package com.studentScholar.assessment;

import com.studentScholar.observer.EventBus;
import com.studentScholar.users.Student;

import java.time.LocalDateTime;

public class ExtensionRequest {
    private String requestId;
    private String reason;
    private String status;
    private LocalDateTime extendedDeadline;

    private Student student;
    private Assessment assessment;
    private EventBus eventBus;

    public ExtensionRequest(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public ExtensionRequest(EventBus eventBus, Student student, Assessment assessment, String reason) {
        this.eventBus = eventBus;
        this.student = student;
        this.assessment = assessment;
        this.reason = reason;
        this.status = "PENDING";
    }

    public void submit() {
    }

    public void updateReason(String reason) {
        this.reason = reason;
    }

    public String getStatus(Student student) {
        if (this.student != student) {
            return null;
        }
        return status;
    }

    public void approve(LocalDateTime extendedDeadline) {
        this.extendedDeadline = extendedDeadline;
        this.status = "APPROVED";
    }

    public void reject() {
        this.status = "REJECTED";
    }

    public LocalDateTime getExtendedDeadline() {
        return extendedDeadline;
    }
}
