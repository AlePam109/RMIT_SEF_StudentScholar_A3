// Rohan Chaudhari
package com.studentScholar.observer;

public class NotificationService implements EventObserver {
    private String serviceId;
    private String notificationType;

    public void update(String event) {
        sendNotification();
    }

    public boolean isActive() {
        return true;
    }

    public void sendNotification() {
        System.out.println("[NotificationService] Confirmation email sent to student");
    }
}
