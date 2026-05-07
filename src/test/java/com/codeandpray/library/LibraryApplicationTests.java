package com.codeandpray.library;

import org.junit.jupiter.api.Test;

/**
 * Smoke-test: приложение корректно собирается.
 * Для полной интеграции (contextLoads) нужна работающая PostgreSQL.
 * Используйте профиль "test" с Testcontainers или запускайте локально.
 */
class LibraryApplicationTests {

    @Test
    void applicationEntrypointExists() {
        // Проверяем, что main-класс существует и компилируется
        Class<?> clazz = LibraryApplication.class;
        assert clazz != null;
    }
}
