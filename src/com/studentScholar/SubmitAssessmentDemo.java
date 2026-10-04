// Rohan Chaudhari
package com.studentScholar;

import com.studentScholar.assessment.Assessment;
import com.studentScholar.assessment.ExtensionRequest;
import com.studentScholar.grading.Rubric;
import com.studentScholar.grading.RubricCriterion;
import com.studentScholar.observer.EventBus;
import com.studentScholar.persistence.Database;
import com.studentScholar.persistence.FileStorage;
import com.studentScholar.users.Student;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashSet;

public final class SubmitAssessmentDemo {
    private SubmitAssessmentDemo() {
    }

    public static void run() {
        FileStorage fileStorage = new FileStorage();
        Database database = new Database();
        EventBus eventBus = new EventBus();
        Rubric rubric = new Rubric("RUBRIC-01", Arrays.asList(new RubricCriterion(
                "CRIT-1", "Overall quality", 10.0,
                new LinkedHashSet<Double>(Arrays.asList(0.0, 5.0, 10.0)))));

        Student student = new Student("STUDENT-001", "Student Demo");

        Assessment openAssessment = new Assessment("ASSESSMENT-01", "Software Design Report",
                rubric, eventBus, fileStorage, database, LocalDateTime.now().plusDays(7));
        System.out.println("Selected: " + openAssessment.selectAssessment(openAssessment));
        student.submitAssessment(openAssessment, "design-report.pdf");

        Assessment closedAssessment = new Assessment("ASSESSMENT-02", "Class Diagram",
                rubric, eventBus, fileStorage, database, LocalDateTime.now().minusDays(1));
        System.out.println("\nSelected: " + closedAssessment.selectAssessment(closedAssessment));
        if (student.submitAssessment(closedAssessment, "class-diagram.pdf") == null) {
            ExtensionRequest request = closedAssessment.requestExtension(student, "Medical certificate provided");
            System.out.println("Extension status: " + request.getStatus(student));
            closedAssessment.updateSubmissionDeadline(student, LocalDateTime.now().plusDays(3));
            System.out.println("Extension status: " + request.getStatus(student));
            student.submitAssessment(closedAssessment, "class-diagram.pdf");
        }
    }
}
