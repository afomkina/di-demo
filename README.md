# DI вручную (Plain Java) + H2: учебный пример

Небольшой проект, демонстрирующий **инъекцию зависимостей вручную** через *Composition Root* без DI-фреймворков.  
Приложение создаёт таблицу `users` в **H2 (in-memory)**, сохраняет пользователей через `JdbcUserRepository` и
«отправляет письма» через `MailClient`.

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
Бизнес-классы (`UserService`) зависят от **интерфейсов** (`NotificationService`, `UserRepository`), поэтому при замене
реализации (например, на SMS-уведомитель) переписывается только сборка в `Main`, а код домена остаётся прежним.

## Тесты

В проекте также есть примеры юнит-тестов, которые показывают, как DI упрощает подмену зависимостей.  
Вместо реальной базы данных и SMTP мы используем **фейковые реализации**:

- `FakeUserRepository` — пишет в консоль сообщение о сохранении e-mail;
- `FakeNotificationService` — пишет в консоль сообщение об отправке письма.

Тесты перехватывают вывод в консоль и проверяют, что при вызове `UserService.registerUser(...)` действительно вызываются
обе зависимости.
Для этого используется аннотация `@StdIo` и тип `StdOut` из библиотеки [JUnit Pioneer](https://junit-pioneer.org/).  
Это удобное расширение к JUnit 5, которое позволяет перехватывать `System.out` и проверять вывод без лишнего кода.  
В реальных проектах чаще используют фреймворк **Mockito**, так как он автоматически создаёт моки и шпионы, делает тесты
короче и позволяет проверять вызовы напрямую через `verify`.

### Как запустить тесты

Выполните команду:

```bash
./gradlew test
```

Результаты будут доступны в отчёте:  
`build/reports/tests/test/index.html`

## Ограничения учебного примера

- H2 работает **в памяти**, данные не сохраняются между запусками.
- В `MailClient` имитация отправки — только лог в консоль.
- Схема минимальна: одна таблица с email.
