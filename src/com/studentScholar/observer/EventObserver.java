// Rohan Chaudhari
package com.studentScholar.observer;

public interface EventObserver {
    void update(String event);

    boolean isActive();
}
