// Rohan Chaudhari
package com.studentScholar.observer;

import java.util.ArrayList;
import java.util.List;

public class EventBus {
    private String eventBusId;
    private List<EventObserver> observers = new ArrayList<>();

    public void subscribe(EventObserver observer) {
        observers.add(observer);
    }

    public void publish(String event) {
        for (EventObserver observer : observers) {
            if (observer.isActive()) {
                observer.update(event);
            }
        }
    }
}
