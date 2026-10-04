// Rohan Chaudhari
package com.studentScholar.communication;

import com.studentScholar.course.CourseShell;
import com.studentScholar.observer.EventBus;

public class Announcement {
    private String announcementId;
    private String message;

    private CourseShell courseShell;
    private EventBus eventBus;

    public Announcement(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void publish() {
    }

    public void updateMessage(String message) {
        this.message = message;
    }
}
