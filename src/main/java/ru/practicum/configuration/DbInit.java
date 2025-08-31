package ru.practicum.configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class DbInit {
    private DbInit() {
    }

    public static void ensureSchema(DataSource dataSource) {
        String ddl = """
                CREATE TABLE IF NOT EXISTS users (
                    email VARCHAR(320) PRIMARY KEY
                )
                """;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(ddl);
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to initialize schema", exception);
        }
    }
}
