-- Добавление 10 авторов
INSERT INTO public.author (name, lastname, birth_date, death_date) VALUES
                                                                       ('Lev', 'Tolstoy', '1828-09-09', '1910-11-20'),
                                                                       ('Ernest', 'Hemingway', '1899-07-21', '1961-07-02'),
                                                                       ('Stephen', 'King', '1947-09-21', NULL),
                                                                       ('Agatha', 'Christie', '1890-09-15', '1976-01-12'),
                                                                       ('Victor', 'Pelevin', '1962-11-22', NULL),
                                                                       ('Haruki', 'Murakami', '1949-01-12', NULL),
                                                                       ('Gabriel', 'Garcia Marquez', '1927-03-06', '2014-04-17'),
                                                                       ('J.R.R.', 'Tolkien', '1892-01-03', '1973-09-02'),
                                                                       ('Ray', 'Bradbury', '1920-08-22', '2012-06-05'),
                                                                       ('Virginia', 'Woolf', '1882-01-25', '1941-03-28');

-- Очисти старый запрос INSERT INTO public.book и замени на этот:
INSERT INTO public.book (title, description, publish_date, page_count, language, price, has_audiobook, reader_time, age_category, isbn, count, status, updated_at)
VALUES
    ('War and Peace', 'Epic historical novel.', '1869-01-01', 1225, 'Russian', 25.99, TRUE, 3000, 'TWELVE_PLUS', '978-0140447934', 10, 'AVAILABLE', NOW()),
    ('The Old Man and the Sea', 'A story of an epic struggle.', '1952-09-01', 127, 'English', 10.50, TRUE, 180, 'ZERO_PLUS', '978-0684801223', 15, 'AVAILABLE', NOW()),
    ('The Shining', 'Horror at the Overlook Hotel.', '1977-01-28', 447, 'English', 14.00, TRUE, 600, 'EIGHTEEN_PLUS', '978-0345806789', 8, 'AVAILABLE', NOW()),
    ('Murder on the Orient Express', 'Hercule Poirot mystery.', '1934-01-01', 256, 'English', 12.00, FALSE, 320, 'TWELVE_PLUS', '978-0007119318', 12, 'AVAILABLE', NOW()),
    ('Generation P', 'Post-Soviet postmodern novel.', '1999-01-01', 350, 'Russian', 18.00, FALSE, 400, 'EIGHTEEN_PLUS', '978-5041024345', 5, 'AVAILABLE', NOW()),
    ('Norwegian Wood', 'A story of loss and sexuality.', '1987-09-04', 296, 'Japanese', 15.00, TRUE, 450, 'SIXTEEN_PLUS', '978-0375704079', 7, 'AVAILABLE', NOW()),
    ('One Hundred Years of Solitude', 'Magical realism masterpiece.', '1967-05-30', 417, 'Spanish', 20.00, FALSE, 800, 'SIXTEEN_PLUS', '978-0060883287', 9, 'AVAILABLE', NOW()),
    ('The Hobbit', 'There and back again.', '1937-09-21', 310, 'English', 16.00, TRUE, 480, 'SIX_PLUS', '978-0547928227', 20, 'AVAILABLE', NOW()),
    ('Fahrenheit 451', 'Dystopian future where books are burned.', '1953-10-19', 158, 'English', 11.00, TRUE, 200, 'TWELVE_PLUS', '978-1451673319', 14, 'AVAILABLE', NOW()),
    ('Mrs Dalloway', 'A day in the life of Clarissa Dalloway.', '1925-05-14', 194, 'English', 13.00, FALSE, 250, 'SIXTEEN_PLUS', '978-0156628709', 4, 'AVAILABLE', NOW());

INSERT INTO public.genre (name) VALUES
                                    ('Classic'), ('Horror'), ('Mystery'), ('Modernism'), ('Dystopia'), ('Fantasy'), ('Magical Realism');

-- Связывание Книг с Авторами (безопасный способ через подзапросы)
INSERT INTO public.book_author (book_id, author_id) VALUES
                                                        ((SELECT id FROM public.book WHERE title = 'War and Peace'), (SELECT id FROM public.author WHERE lastname = 'Tolstoy')),
                                                        ((SELECT id FROM public.book WHERE title = 'The Old Man and the Sea'), (SELECT id FROM public.author WHERE lastname = 'Hemingway')),
                                                        ((SELECT id FROM public.book WHERE title = 'The Shining'), (SELECT id FROM public.author WHERE lastname = 'King')),
                                                        ((SELECT id FROM public.book WHERE title = 'Murder on the Orient Express'), (SELECT id FROM public.author WHERE lastname = 'Christie')),
                                                        ((SELECT id FROM public.book WHERE title = 'Generation P'), (SELECT id FROM public.author WHERE lastname = 'Pelevin')),
                                                        ((SELECT id FROM public.book WHERE title = 'Norwegian Wood'), (SELECT id FROM public.author WHERE lastname = 'Murakami')),
                                                        ((SELECT id FROM public.book WHERE title = 'One Hundred Years of Solitude'), (SELECT id FROM public.author WHERE lastname = 'Garcia Marquez')),
                                                        ((SELECT id FROM public.book WHERE title = 'The Hobbit'), (SELECT id FROM public.author WHERE lastname = 'Tolkien')),
                                                        ((SELECT id FROM public.book WHERE title = 'Fahrenheit 451'), (SELECT id FROM public.author WHERE lastname = 'Bradbury')),
                                                        ((SELECT id FROM public.book WHERE title = 'Mrs Dalloway'), (SELECT id FROM public.author WHERE lastname = 'Woolf'));

INSERT INTO public.book_genre (book_id, genre_id) VALUES
                                                      ((SELECT id FROM public.book WHERE title = 'War and Peace'), (SELECT id FROM public.genre WHERE name = 'Classic')),
                                                      ((SELECT id FROM public.book WHERE title = 'The Old Man and the Sea'), (SELECT id FROM public.genre WHERE name = 'Classic')),
                                                      ((SELECT id FROM public.book WHERE title = 'The Shining'), (SELECT id FROM public.genre WHERE name = 'Horror')),
                                                      ((SELECT id FROM public.book WHERE title = 'Murder on the Orient Express'), (SELECT id FROM public.genre WHERE name = 'Mystery')),
                                                      ((SELECT id FROM public.book WHERE title = 'Generation P'), (SELECT id FROM public.genre WHERE name = 'Modernism')),
                                                      ((SELECT id FROM public.book WHERE title = 'One Hundred Years of Solitude'), (SELECT id FROM public.genre WHERE name = 'Magical Realism')),
                                                      ((SELECT id FROM public.book WHERE title = 'The Hobbit'), (SELECT id FROM public.genre WHERE name = 'Fantasy')),
                                                      ((SELECT id FROM public.book WHERE title = 'Fahrenheit 451'), (SELECT id FROM public.genre WHERE name = 'Dystopia'));

INSERT INTO public.users (user_name, user_lastname, user_email, user_phone, user_password, user_registration_date, user_role) VALUES
    ('Test', 'User', 'test@example.com', '1234567', 'pass', '2023-10-01', 'USER');

INSERT INTO public.loans (book_id, user_id, loan_date, return_date, status) VALUES
    ((SELECT id FROM public.book WHERE title = 'The Hobbit'), (SELECT user_id FROM public.users WHERE user_email = 'test@example.com'), '2023-11-01', '2023-11-15', 'ISSUED');