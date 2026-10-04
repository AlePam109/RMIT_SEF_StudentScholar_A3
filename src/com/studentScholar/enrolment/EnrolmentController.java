// Prabhuta
package com.studentScholar.enrolment;

import com.studentScholar.course.CourseShell;
import com.studentScholar.users.Student;

import java.util.List;

/** Control class coordinating the Bulk Enrol Students sequence. */
public class EnrolmentController {
    // Prabhuta
    private BulkEnrolPage enrolUI;
    private CourseShell courseShell;
    private EnrolmentCSV enrolmentCSV;

    // Prabhuta
    public EnrolmentController(BulkEnrolPage enrolUI, CourseShell courseShell, EnrolmentCSV enrolmentCSV) {
        this.enrolUI = enrolUI;
        this.courseShell = courseShell;
        this.enrolmentCSV = enrolmentCSV;
    }

    // Prabhuta
    // 1.1 -> 1.1.1 verifyCourseShell, 1.1.2 courseShellAvailable, 1.2 returned to the page
    public CourseShell selectCourseShell(String courseId) {
        return courseShell.verifyCourseShell(courseId);
    }

    // Prabhuta
    // 2.1 -> 2.2 requestEnrolmentCSV (the page then shows the prompt, 2.3)
    public void selectBulkEnrolStudents() {
        enrolUI.requestEnrolmentCSV();
    }

    // Prabhuta
    // 3.1 -> returns true when the enrolment data was valid
    public boolean provideCSV(String file) {
        enrolmentCSV.uploadCSV(file);                        // 3.1.1
        List<String> emailList = enrolmentCSV.readEmails();  // 3.1.2, 3.1.3

        // alt [required email information valid]
        if (isValid(emailList)) {
            // par - operand 1: enrol students in selected course shell
            // loop [for each student email]
            for (String email : emailList) {
                Student student = Student.findStudent(email);        // 3.2a.1, 3.2a.2
                if (student != null) {
                    courseShell.addStudent(student);                 // 3.2a.3, 3.2a.4
                }
            }
            // par - operand 2: prepare bulk enrolment confirmation
            prepareEnrolmentConfirmation();                          // 3.2a.5

            enrolUI.enrolmentConfirmed();                            // 3.3a (then 3.4a)
            return true;
        }

        // alt [required email information invalid]
        enrolUI.reportInvalidEnrolmentData();                        // 3.2b (then 3.3b)
        return false;
    }

    // Prabhuta
    private void prepareEnrolmentConfirmation() {
        System.out.println("Controller: preparing bulk enrolment confirmation.");
    }

    // Prabhuta
    private boolean isValid(List<String> emailList) {
        if (emailList == null || emailList.isEmpty()) {
            return false;
        }
        for (String email : emailList) {
            if (email == null || !email.contains("@")) {
                return false;
            }
        }
        return true;
    }
}
