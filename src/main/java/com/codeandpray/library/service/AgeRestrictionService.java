package com.codeandpray.library.service;

import com.codeandpray.library.entity.Book;
import com.codeandpray.library.entity.User;
import com.codeandpray.library.exception.security.AgeRestrictionViolated;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class AgeRestrictionService {

    public void checkUserCanAccessBook(User user, Book book) {
        if (book.getAgeCategory() == null) {
            return;
        }

        if (user.getBirthDate() == null) {
            throw new AgeRestrictionViolated(
                    "Требуется дата рождения пользователя"
            );
        }

        int userAge = Period.between(user.getBirthDate(), LocalDate.now()).getYears();
        int requiredAge = book.getAgeCategory().getMinAge();

        if (userAge < requiredAge) {
            throw new AgeRestrictionViolated(
                    "Возраст пользователя ниже требуемой возрастной категории " + book.getAgeCategory().getValue()
            );
        }
    }
}