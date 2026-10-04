// Alec Pham
package com.studentScholar;

import com.studentScholar.assessment.Assessment;
import com.studentScholar.assessment.Submission;
import com.studentScholar.course.Course;
import com.studentScholar.grading.*;
import com.studentScholar.users.Student;
import com.studentScholar.users.TeachingStaff;

import java.util.Arrays;
import java.util.LinkedHashSet;

/** Runs a deterministic demonstration of the approved Grade Submission sequence. */
public final class GradingDemo {
    private GradingDemo() {
    }

    public static void run() {
        TeachingStaff staff = new TeachingStaff("STAFF-101", "Teaching Staff Demo");

        RubricCriterion analysis = new RubricCriterion(
                "CRIT-1",
                "Analysis and reasoning",
                10.0,
                new LinkedHashSet<Double>(Arrays.asList(0.0, 2.5, 5.0, 7.5, 10.0)));
        RubricCriterion communication = new RubricCriterion(
                "CRIT-2",
                "Technical communication",
                10.0,
                new LinkedHashSet<Double>(Arrays.asList(0.0, 2.5, 5.0, 7.5, 10.0)));
        Rubric rubric = new Rubric("RUBRIC-01", Arrays.asList(analysis, communication));

        Assessment assessment = new Assessment(
                "ASSESSMENT-01",
                "Software Design Report",
                rubric);
        Student student = new Student("STUDENT-001", "Student Demo");
        Submission submission = new Submission(
                "SUBMISSION-01",
                student,
                "design-report.pdf",
                "Submitted assessment content");

        // Setup occurs before the interaction, as documented in the sequence note.
        submission.attachDraftGrade(new GradeRecord("GRADE-01"));
        assessment.addSubmission(submission);

        Course course = new Course("COURSE-01", "Software Engineering Fundamentals");
        course.assignStaff(staff);
        course.addAssessment(assessment);

        GradingPage page = new GradingPage();
        GradingController controller = new GradingController(
                staff,
                page,
                course,
                new SimilarityCheckService(true));
        page.bindController(controller);

        page.openAssignedCourse("COURSE-01");
        page.selectAssessment("ASSESSMENT-01");
        page.selectSubmission("SUBMISSION-01");
        page.requestSimilarityCheck();

        // Standard rubric mark branch.
        page.submitCriterionMark("CRIT-1", 7.5, "Strong analysis.");

        // Custom mark branch followed by the required justification.
        page.submitCriterionMark("CRIT-2", 8.0, "Clear and professional presentation.");
        page.submitJustification("The work falls between the 7.5 and 10 descriptors.");

        page.saveDraft("A well-structured submission with sound technical reasoning.");
    }
}
