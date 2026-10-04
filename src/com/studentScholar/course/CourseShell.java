// Rohan Chaudhari
package com.studentScholar.course;

import com.studentScholar.communication.Announcement;
import com.studentScholar.users.Administrator;
import com.studentScholar.users.Student;

import java.util.ArrayList;
import java.util.List;

public class CourseShell {
    private String shellId;
    private boolean locked;

    private Course course;
    private Administrator administrator;
    private List<Announcement> announcements = new ArrayList<>();
    // Prabhuta
    private List<Student> students = new ArrayList<>();

    // Prabhuta
    public CourseShell() {
    }

    // Prabhuta
    public CourseShell(String shellId) {
        this.shellId = shellId;
    }

    public void lock() {
        locked = true;
    }

    public void unlock() {
        locked = false;
    }

    // Prabhuta
    public CourseShell verifyCourseShell(String courseId) {
        if (shellId != null && shellId.equals(courseId)) {
            return this;
        }
        return null;
    }

    // Prabhuta
    public boolean addStudent(Student student) {
        if (student != null) {
            students.add(student);
            return true;
        }
        return false;
    }

    // Prabhuta
    public int getStudentCount() {
        return students.size();
    }
}
