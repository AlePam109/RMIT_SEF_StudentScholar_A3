// Rohan Chaudhari
package com.studentScholar.course;

import com.studentScholar.communication.Announcement;
import com.studentScholar.users.Administrator;

import java.util.ArrayList;
import java.util.List;

public class CourseShell {
    private String shellId;
    private boolean locked;

    private Course course;
    private Administrator administrator;
    private List<Announcement> announcements = new ArrayList<>();

    public void lock() {
        locked = true;
    }

    public void unlock() {
        locked = false;
    }
}
