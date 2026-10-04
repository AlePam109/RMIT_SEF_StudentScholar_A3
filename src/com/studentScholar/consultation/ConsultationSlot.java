// Rohan Chaudhari
package com.studentScholar.consultation;

import com.studentScholar.course.Course;
import com.studentScholar.users.TeachingStaff;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConsultationSlot {
    private String slotId;
    private LocalDateTime startTime;

    private Course course;
    private TeachingStaff teachingStaff;
    private List<Booking> bookings = new ArrayList<>();

    public void createSlot() {
    }

    public boolean isAvailable() {
        return false;
    }
}
