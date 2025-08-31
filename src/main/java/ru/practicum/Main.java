package ru.practicum;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.practicum.client.MailClient;
import ru.practicum.configuration.DbInit;
import ru.practicum.repository.JdbcUserRepository;
import ru.practicum.repository.UserRepository;
import ru.practicum.service.notification.EmailNotificationService;
import ru.practicum.service.notification.NotificationService;
import ru.practicum.service.user.UserService;

public class Main {
    public static void main(String[] args) {
        // --- Composition Root: настраиваем инфраструктуру и связываем зависимости ---

        // 1) DataSource
        HikariDataSource dataSource = new HikariDataSource(hikariConfig());

        // 2) Инициализируем схему
        DbInit.ensureSchema(dataSource);

        // 3) Инфраструктура уведомлений
        MailClient mailClient = new MailClient("smtp.example.org");
        NotificationService notifier = new EmailNotificationService(mailClient);

        // 4) Репозиторий и доменный сервис
        UserRepository repo = new JdbcUserRepository(dataSource);
        UserService userService = new UserService(notifier, repo);

        // --- Регистрируем пользователей ---
        userService.registerUser("user@mail.com");
        userService.registerUser("another@mail.com");

        // Закрываем пул соединений
        dataSource.close();
    }

    private static HikariConfig hikariConfig() {
        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl("jdbc:h2:mem:demo;DB_CLOSE_DELAY=-1;MODE=PostgreSQL");
        cfg.setUsername("sa");
        cfg.setPassword("");
        cfg.setMaximumPoolSize(4);
        cfg.setAutoCommit(true);
        return cfg;
    }
}