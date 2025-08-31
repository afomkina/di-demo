package ru.practicum.service.notification;

public class FakeNotificationService implements NotificationService {
    @Override
    public void sendWelcome(String email) {
        System.out.println("FAKE: sending welcome to " + email);
    }
}
