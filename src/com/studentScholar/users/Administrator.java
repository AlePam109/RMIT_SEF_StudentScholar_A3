// Rohan Chaudhari
package com.studentScholar.users;

import com.studentScholar.course.CourseShell;
import com.studentScholar.course.Enrolment;
import com.studentScholar.course.ExternalTool;

import java.util.ArrayList;
import java.util.List;

public class Administrator extends User {
    private String adminLevel;
    private List<CourseShell> managedCourseShells = new ArrayList<>();
    private List<Enrolment> managedEnrolments = new ArrayList<>();
    private List<ExternalTool> managedExternalTools = new ArrayList<>();

    public Administrator(String userId, String name) {
        super(userId, name);
    }

    public void lockCourseShell(CourseShell shell) {
    }
}
