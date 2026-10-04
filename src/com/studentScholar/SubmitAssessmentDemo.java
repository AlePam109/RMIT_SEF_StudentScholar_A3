// Rohan Chaudhari
package com.studentScholar;

import com.studentScholar.assessment.Assessment;
import com.studentScholar.assessment.ExtensionRequest;
import com.studentScholar.grading.Rubric;
import com.studentScholar.grading.RubricCriterion;
import com.studentScholar.observer.EventBus;
import com.studentScholar.observer.NotificationService;
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
        eventBus.subscribe(new NotificationService());
        Rubric rubric = new Rubric("RUBRIC-01", Arrays.asList(new RubricCriterion(
                "CRIT-1", "Overall quality", 10.0,
                new LinkedHashSet<Double>(Arrays.asList(0.0, 5.0, 10.0)))));
        Student student = new Student("STUDENT-001", "Student Demo");

        System.out.println("-- Within submission period, no extension --");
        Assessment openAssessment = new Assessment("ASSESSMENT-01", "Software Design Report",
                rubric, eventBus, fileStorage, database, LocalDateTime.now().plusDays(7));
        System.out.println("Selected: " + openAssessment.selectAssessment(openAssessment));
        student.submitAssessment(openAssessment, "design-report.pdf");

        System.out.println("\n-- Outside submission period, extension approved --");
        Assessment lateAssessment = new Assessment("ASSESSMENT-02", "Class Diagram",
                rubric, eventBus, fileStorage, database, LocalDateTime.now().minusDays(1));
        System.out.println("Selected: " + lateAssessment.selectAssessment(lateAssessment));
        ExtensionRequest request = lateAssessment.requestExtension(student, "Medical certificate provided");
        request.approve(LocalDateTime.now().plusDays(3));
        student.submitAssessment(lateAssessment, "class-diagram.pdf");

        System.out.println("\n-- Outside submission period, no extension --");
        Assessment closedAssessment = new Assessment("ASSESSMENT-03", "Test Plan",
                rubric, eventBus, fileStorage, database, LocalDateTime.now().minusDays(1));
        System.out.println("Selected: " + closedAssessment.selectAssessment(closedAssessment));
        student.submitAssessment(closedAssessment, "test-plan.pdf");
    }
}
