CREATE TABLE IF NOT EXISTS public.author (
                                             id BIGSERIAL PRIMARY KEY,
                                             name VARCHAR(255),
                                             lastname VARCHAR(255),
                                             birth_date DATE,
                                             death_date DATE
);

CREATE TABLE IF NOT EXISTS public.users (
                                            user_id BIGSERIAL PRIMARY KEY,
                                            user_name VARCHAR(255),
                                            user_lastname VARCHAR(255),
                                            user_email VARCHAR(255),
                                            user_phone VARCHAR(255),
                                            user_password VARCHAR(255),
                                            user_registration_date DATE,
                                            user_role VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS public.book (
                                           id BIGSERIAL PRIMARY KEY,
                                           title VARCHAR(255) NOT NULL,
                                           description VARCHAR(1000),
                                           publish_date TIMESTAMP,
                                           page_count INTEGER NOT NULL,
                                           language VARCHAR(255) NOT NULL,
                                           price REAL NOT NULL,
                                           has_audiobook BOOLEAN DEFAULT FALSE,
                                           reader_time INTEGER,
                                           age_category VARCHAR(20),
                                           isbn VARCHAR(255) NOT NULL UNIQUE,
                                           count INTEGER NOT NULL DEFAULT 0,
                                           updated_at TIMESTAMP,
                                           status VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS public.genre (
                                     id BIGSERIAL PRIMARY KEY,
                                     genre_name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS public.book_author (
                                                  book_id BIGINT NOT NULL REFERENCES public.book(id) ON DELETE CASCADE,
                                                  author_id BIGINT NOT NULL REFERENCES public.author(id) ON DELETE CASCADE,
                                                  PRIMARY KEY (book_id, author_id)
);

CREATE TABLE IF NOT EXISTS public.book_genre (
                                                 book_id BIGINT NOT NULL REFERENCES public.book(id) ON DELETE CASCADE,
                                                 genre_id BIGINT NOT NULL REFERENCES public.genre(id) ON DELETE CASCADE,
                                                 PRIMARY KEY (book_id, genre_id)
);

CREATE TABLE IF NOT EXISTS public.book_popularities (
                                                        popularity_id BIGSERIAL PRIMARY KEY,
                                                        book_id BIGINT NOT NULL REFERENCES public.book(id),
                                                        read_count INTEGER NOT NULL,
                                                        period VARCHAR(20) NOT NULL,
                                                        calculated_at TIMESTAMP NOT NULL,
                                                        CONSTRAINT uk_book_period UNIQUE (book_id, period)
);

CREATE TABLE IF NOT EXISTS public.loans (
                                            id BIGSERIAL PRIMARY KEY,
                                            book_id BIGINT REFERENCES public.book(id),
                                            user_id BIGINT REFERENCES public.users(user_id),
                                            loan_date DATE,
                                            return_date DATE,
                                            actual_return_date DATE,
                                            status VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS public.fines (
                                            fine_id BIGSERIAL PRIMARY KEY,
                                            loan_id BIGINT REFERENCES public.loans(id),
                                            fine_amount DECIMAL(19, 2),
                                            fine_reason VARCHAR(255),
                                            fine_status VARCHAR(20),
                                            created_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS public.ratings (
                                              rating_id BIGSERIAL PRIMARY KEY,
                                              book_id BIGINT NOT NULL REFERENCES public.book(id),
                                              user_id BIGINT NOT NULL REFERENCES public.users(user_id),
                                              score INTEGER NOT NULL,
                                              created_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS public.reservation (
                                                  id BIGSERIAL PRIMARY KEY,
                                                  book_id BIGINT REFERENCES public.book(id),
                                                  user_id BIGINT REFERENCES public.users(user_id),
                                                  reservation_date TIMESTAMP,
                                                  status VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS public.reviews (
                                              id BIGSERIAL PRIMARY KEY,
                                              book_id BIGINT NOT NULL REFERENCES public.book(id),
                                              user_id BIGINT NOT NULL,
                                              rating INTEGER NOT NULL,
                                              comment VARCHAR(1000),
                                              created_at TIMESTAMP NOT NULL
);
CREATE TABLE IF NOT EXISTS public.editions (
                                               id BIGSERIAL PRIMARY KEY,
                                               book_id BIGINT NOT NULL,
                                               edition_number INTEGER NOT NULL,
                                               publisher VARCHAR(255) NOT NULL,
                                               publish_date DATE NOT NULL,

    CONSTRAINT fk_editions_book FOREIGN KEY (book_id) REFERENCES public.book(id) ON DELETE CASCADE
    );