package ru.practicum.service.user;

import ru.practicum.repository.UserRepository;
import ru.practicum.service.notification.NotificationService;

public class UserService {
    private final NotificationService notificationService;
    private final UserRepository userRepository;

    public UserService(NotificationService notificationService, UserRepository userRepository) {
        this.notificationService = notificationService;
        this.userRepository = userRepository;
    }

    public void registerUser(String email) {
        userRepository.save(email);
        notificationService.sendWelcome(email);
    }
}
