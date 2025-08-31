package ru.practicum.service.notification;


import ru.practicum.client.MailClient;

public class EmailNotificationService implements NotificationService {
    private final MailClient client;

    public EmailNotificationService(MailClient client) {
        this.client = client;
    }

    @Override
    public void sendWelcome(String email) {
        client.send(email, "Welcome " + email + "!");
    }
}
