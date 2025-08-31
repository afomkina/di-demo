package ru.practicum.service.user;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;
import ru.practicum.repository.FakeUserRepository;
import ru.practicum.service.notification.FakeNotificationService;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {

    @Test
    @StdIo
    void registerUser_writesToConsole(StdOut out) {
        var service = new UserService(
                new FakeNotificationService(),
                new FakeUserRepository()
        );

        service.registerUser("test@mail.com");

        assertThat(out.capturedLines()).contains(
                "FAKE: saving user test@mail.com",
                "FAKE: sending welcome to test@mail.com"
        );
    }
}
