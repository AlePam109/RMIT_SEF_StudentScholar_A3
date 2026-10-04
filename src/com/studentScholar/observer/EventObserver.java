// Rohan Chaudhari
package com.studentScholar.observer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

interface EventObserver {
    void update(String event);

    boolean isActive();
}
