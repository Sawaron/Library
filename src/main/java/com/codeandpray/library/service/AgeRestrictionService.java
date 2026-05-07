package com.codeandpray.library.service;

import com.codeandpray.library.entity.Book;
import com.codeandpray.library.entity.User;
import com.codeandpray.library.exception.security.AgeRestrictionViolated;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Set;

@Service
public class AgeRestrictionService {

    private static final Set<String> PRIVILEGED_ROLES = Set.of("LIBRARIAN", "ADMIN");

    public void checkUserCanAccessBook(User user, Book book) {
        if (book.getAgeCategory() == null) {
            return;
        }

        // LIBRARIAN и ADMIN имеют полный доступ ко всем возрастным категориям
        if (user.getRole() != null && PRIVILEGED_ROLES.contains(user.getRole().toUpperCase())) {
            return;
        }

        int requiredAge = book.getAgeCategory().getMinAge();
        if (requiredAge == 0) {
            return;
        }

        if (user.getBirthDate() == null) {
            throw new AgeRestrictionViolated(
                    "Для доступа к контенту " + book.getAgeCategory().getValue() +
                    " необходимо указать дату рождения в профиле"
            );
        }

        int userAge = Period.between(user.getBirthDate(), LocalDate.now()).getYears();

        if (userAge < requiredAge) {
            throw new AgeRestrictionViolated(
                    "Доступ ограничен: требуется возраст " + book.getAgeCategory().getValue() +
                    ", ваш возраст: " + userAge + " лет"
            );
        }
    }
}
