# DI вручную (Plain Java) + H2: учебный пример

Небольшой проект, демонстрирующий **инъекцию зависимостей вручную** через *Composition Root* без DI-фреймворков.  
Приложение создаёт таблицу `users` в **H2 (in-memory)**, сохраняет пользователей через `JdbcUserRepository` и «отправляет письма» через `MailClient`.

## Как запустить

1. Соберите проект из корня:
   ```bash
   ./gradlew clean build
   ```
   
2. Запустите приложение.

   ```bash
   java -jar build/libs/di-demo-1.0-SNAPSHOT-all.jar
   ```

## Что происходит при запуске

1. **Создаётся DataSource** (HikariCP).

2. **Инициализируется схема** вызовом `DbInit.ensureSchema(dataSource)`:  
   создаётся таблица `users(email VARCHAR(320) PRIMARY KEY)`.

3. **Собираются зависимости вручную**:
    - `MailClient("smtp.example.org")`
    - `NotificationService notifier = new EmailNotificationService(mailClient)`
    - `UserRepository repo = new JdbcUserRepository(dataSource)`
    - `UserService userService = new UserService(notifier, repo)`

4. **Вызывается бизнес-операция**:
   `userService.registerUser("...")` — сохраняет e-mail в `users` и «шлёт письмо».

5. **Закрывается пул**: `dataSource.close()`.

## Ключевая идея: Composition Root

Все решения о **конкретных реализациях** находятся в одном месте — в `Main`.  
Бизнес-классы (`UserService`) зависят от **интерфейсов** (`NotificationService`, `UserRepository`), поэтому при замене реализации (например, на SMS-уведомитель) переписывается только сборка в `Main`, а код домена остаётся прежним.


## Ограничения учебного примера

- H2 работает **в памяти**, данные не сохраняются между запусками.
- В `MailClient` имитация отправки — только лог в консоль.
- Схема минимальна: одна таблица с email.
