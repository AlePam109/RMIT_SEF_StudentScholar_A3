// Prabhuta
package com.studentScholar.enrolment;

import com.studentScholar.course.CourseShell;
import com.studentScholar.users.Administrator;

/** Boundary class responsible for administrator input and presentation output. */
public class BulkEnrolPage {
    // Prabhuta
    private EnrolmentController enrolmentController;
    private Administrator administrator;

    // Prabhuta
    public BulkEnrolPage(Administrator administrator) {
        this.administrator = administrator;
    }

    // Prabhuta
    public void bindController(EnrolmentController enrolmentController) {
        this.enrolmentController = enrolmentController;
    }

    // Prabhuta
    // 1 -> 1.1, the result of 1.1.1/1.1.2 comes back as 1.2
    public void selectCourseShell(String courseId) {
        CourseShell courseShell = enrolmentController.selectCourseShell(courseId);
        if (courseShell != null) {
            showCourseShell(courseShell);
        } else {
            System.out.println("Course shell not found: " + courseId);
        }
    }

    // Prabhuta
    // 1.3
    public void showCourseShell(CourseShell courseShell) {
        System.out.println(administrator.getName() + " sees the selected course shell.");
    }

    // Prabhuta
    // 2 -> 2.1
    public void selectBulkEnrolStudents() {
        enrolmentController.selectBulkEnrolStudents();
    }

    // Prabhuta
    // 2.2 (called by the controller), then 2.3
    public void requestEnrolmentCSV() {
        showCSVUploadPrompt();
    }

    // Prabhuta
    // 2.3
    public void showCSVUploadPrompt() {
        System.out.println(administrator.getName() + " sees: please upload the enrolment CSV.");
    }

    // Prabhuta
    // 3 -> 3.1
    public boolean provideCSV(String file) {
        return enrolmentController.provideCSV(file);
    }

    // Prabhuta
    // 3.3a (called by the controller), then 3.4a
    public void enrolmentConfirmed() {
        showBulkEnrolmentConfirmation();
    }

    // Prabhuta
    // 3.4a
    public void showBulkEnrolmentConfirmation() {
        System.out.println(administrator.getName() + " sees: bulk enrolment confirmed.");
    }

    // Prabhuta
    // 3.2b (called by the controller), then 3.3b
    public void reportInvalidEnrolmentData() {
        showInvalidEnrolmentDataError();
    }

    // Prabhuta
    // 3.3b
    public void showInvalidEnrolmentDataError() {
        System.out.println(administrator.getName() + " sees: invalid enrolment data.");
    }
}
