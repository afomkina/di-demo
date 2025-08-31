package ru.practicum.client;

public class MailClient {
    private final String smtpHost;

    public MailClient(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public void send(String to, String text) {
        // Демонстрационная "отправка"
        System.out.println("Connecting to SMTP: " + smtpHost);
        System.out.println("Sending to " + to + ": " + text);
    }
}
