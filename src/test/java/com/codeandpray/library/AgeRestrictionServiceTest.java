package com.codeandpray.library;

import com.codeandpray.library.entity.Book;
import com.codeandpray.library.entity.User;
import com.codeandpray.library.enums.AgeCategory;
import com.codeandpray.library.exception.security.AgeRestrictionViolated;
import com.codeandpray.library.service.AgeRestrictionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AgeRestrictionServiceTest {

    private AgeRestrictionService service;

    @BeforeEach
    void setUp() {
        service = new AgeRestrictionService();
    }

    private User userWithAge(int age, String role) {
        return User.builder()
                .id(1L)
                .birthDate(LocalDate.now().minusYears(age))
                .role(role)
                .build();
    }

    private Book bookWithCategory(AgeCategory category) {
        return Book.builder().id(1L).title("Test").ageCategory(category).build();
    }

    // BR-29: Пользователь 15 лет не может получить книгу 18+
    @Test
    @DisplayName("BR-29: Пользователь 15 лет — доступ к книге 18+ запрещён (403)")
    void minor15_cannotAccess18plus() {
        User minor = userWithAge(15, "READER");
        Book book18 = bookWithCategory(AgeCategory.EIGHTEEN_PLUS);

        assertThatThrownBy(() -> service.checkUserCanAccessBook(minor, book18))
                .isInstanceOf(AgeRestrictionViolated.class);
    }

    // BR-29: Пользователь 18 лет может получить книгу 18+
    @Test
    @DisplayName("BR-29: Пользователь 18 лет — доступ к книге 18+ разрешён")
    void adult18_canAccess18plus() {
        User adult = userWithAge(18, "READER");
        Book book18 = bookWithCategory(AgeCategory.EIGHTEEN_PLUS);

        assertThatCode(() -> service.checkUserCanAccessBook(adult, book18))
                .doesNotThrowAnyException();
    }

    // BR-29: Без birthDate — блокировать доступ к 16+ и 18+
    @Test
    @DisplayName("BR-29: Нет birthDate → доступ к 16+ заблокирован (Fail-Secure)")
    void noBirthDate_cannotAccessRestricted() {
        User user = User.builder().id(2L).role("READER").birthDate(null).build();
        Book book16 = bookWithCategory(AgeCategory.SIXTEEN_PLUS);

        assertThatThrownBy(() -> service.checkUserCanAccessBook(user, book16))
                .isInstanceOf(AgeRestrictionViolated.class);
    }

    // LIBRARIAN имеет полный доступ
    @Test
    @DisplayName("Роль LIBRARIAN — полный доступ ко всем возрастным категориям")
    void librarian_canAccessAllCategories() {
        User librarian = User.builder().id(3L).role("LIBRARIAN").birthDate(null).build();
        Book book18 = bookWithCategory(AgeCategory.EIGHTEEN_PLUS);

        assertThatCode(() -> service.checkUserCanAccessBook(librarian, book18))
                .doesNotThrowAnyException();
    }

    // ADMIN имеет полный доступ
    @Test
    @DisplayName("Роль ADMIN — полный доступ ко всем возрастным категориям")
    void admin_canAccessAllCategories() {
        User admin = User.builder().id(4L).role("ADMIN").birthDate(null).build();
        Book book18 = bookWithCategory(AgeCategory.EIGHTEEN_PLUS);

        assertThatCode(() -> service.checkUserCanAccessBook(admin, book18))
                .doesNotThrowAnyException();
    }

    // Книга 0+ — доступна всем
    @Test
    @DisplayName("Книга 0+ — доступна пользователю любого возраста")
    void book0plus_accessibleToAll() {
        User child = userWithAge(5, "READER");
        Book book0 = bookWithCategory(AgeCategory.ZERO_PLUS);

        assertThatCode(() -> service.checkUserCanAccessBook(child, book0))
                .doesNotThrowAnyException();
    }
}
