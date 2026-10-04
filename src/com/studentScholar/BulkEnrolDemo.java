// Prabhuta
package com.studentScholar;

import com.studentScholar.course.CourseShell;
import com.studentScholar.enrolment.BulkEnrolPage;
import com.studentScholar.enrolment.EnrolmentCSV;
import com.studentScholar.enrolment.EnrolmentController;
import com.studentScholar.users.Administrator;

/** Runs a deterministic demonstration of the Bulk Enrol Students sequence. */
public final class BulkEnrolDemo {
    private BulkEnrolDemo() {
    }

    // Prabhuta
    public static void run() {
        Administrator administrator = new Administrator("ADMIN-001", "Administrator Demo");
        CourseShell courseShell = new CourseShell("COMP101");
        EnrolmentCSV enrolmentCSV = new EnrolmentCSV();

        BulkEnrolPage page = new BulkEnrolPage(administrator);
        EnrolmentController controller = new EnrolmentController(page, courseShell, enrolmentCSV);
        page.bindController(controller);

        page.selectCourseShell("COMP101");   // 1 - 1.3
        page.selectBulkEnrolStudents();      // 2 - 2.3

        // loop [for each enrolment attempt]
        // 1st attempt: invalid file -> 3.2b, 3.3b
        // 2nd attempt: valid file   -> 3.2a.x, 3.3a, 3.4a
        String[] attempts = {"students.txt", "students.csv"};
        for (String file : attempts) {
            boolean valid = page.provideCSV(file);   // 3
            if (valid) {
                break;
            }
        }

        System.out.println("Students enrolled: " + courseShell.getStudentCount());
    }
}
