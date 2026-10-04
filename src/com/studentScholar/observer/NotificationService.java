// Rohan Chaudhari
package com.studentScholar.observer;

class NotificationService implements EventObserver {
    private String serviceId;
    private String notificationType;

    public void update(String event) {
    }

    public boolean isActive() {
        return true;
    }

    public void sendNotification() {
    }
}
