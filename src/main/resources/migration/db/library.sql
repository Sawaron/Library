ALTER TABLE IF EXISTS "user" RENAME TO users;
ALTER TABLE users RENAME COLUMN reader_id TO user_id;
ALTER TABLE users RENAME COLUMN reader_name TO user_name;
ALTER TABLE users RENAME COLUMN reader_lastname TO user_lastname;
ALTER TABLE users RENAME COLUMN reader_phone TO user_phone;
ALTER TABLE users RENAME COLUMN reader_email TO user_email;
ALTER TABLE users RENAME COLUMN reader_registration_date TO user_registration_date;
ALTER TABLE users ADD COLUMN IF NOT EXISTS user_password VARCHAR(255);
ALTER TABLE users ADD COLUMN IF NOT EXISTS user_role VARCHAR(50);
ALTER TABLE users DROP COLUMN IF EXISTS reader_address;
ALTER TABLE users DROP COLUMN IF EXISTS reader_status;

ALTER TABLE book DROP CONSTRAINT IF EXISTS fk_book_author;
ALTER TABLE book DROP COLUMN IF EXISTS author_id;
ALTER TABLE book DROP COLUMN IF EXISTS status;
ALTER TABLE book RENAME COLUMN summary TO description;
ALTER TABLE book RENAME COLUMN created_at TO publish_date;
ALTER TABLE book ADD COLUMN IF NOT EXISTS page_count INTEGER NOT NULL DEFAULT 0;
ALTER TABLE book ADD COLUMN IF NOT EXISTS language VARCHAR(50) NOT NULL DEFAULT 'Unknown';
ALTER TABLE book ADD COLUMN IF NOT EXISTS price FLOAT DEFAULT 0.0;
ALTER TABLE book ADD COLUMN IF NOT EXISTS has_audiobook BOOLEAN DEFAULT FALSE;
ALTER TABLE book ADD COLUMN IF NOT EXISTS reader_time INTEGER;
ALTER TABLE book ADD COLUMN IF NOT EXISTS age_category VARCHAR(50);
ALTER TABLE book ADD COLUMN IF NOT EXISTS count INTEGER DEFAULT 0;

CREATE TABLE IF NOT EXISTS book_author (
                                           book_id BIGINT NOT NULL,
                                           author_id BIGINT NOT NULL,
                                           PRIMARY KEY (book_id, author_id),
                                           CONSTRAINT fk_ba_book FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE CASCADE,
                                           CONSTRAINT fk_ba_author FOREIGN KEY (author_id) REFERENCES author(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS genres (
                                      id BIGSERIAL PRIMARY KEY,
                                      name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS book_genre (
                                          book_id BIGINT NOT NULL,
                                          genre_id BIGINT NOT NULL,
                                          PRIMARY KEY (book_id, genre_id),
                                          CONSTRAINT fk_bg_book FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE CASCADE,
                                          CONSTRAINT fk_bg_genre FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS editions (
                                        id BIGSERIAL PRIMARY KEY,
                                        book_id BIGINT NOT NULL REFERENCES book(id) ON DELETE CASCADE,
                                        edition_number INTEGER NOT NULL,
                                        publisher VARCHAR(255) NOT NULL,
                                        publish_date DATE DEFAULT CURRENT_DATE
);

CREATE TABLE IF NOT EXISTS ratings (
                                       rating_id BIGSERIAL PRIMARY KEY,
                                       book_id BIGINT NOT NULL REFERENCES book(id) ON DELETE CASCADE,
                                       user_id BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
                                       score INTEGER NOT NULL CHECK (score >= 1 AND score <= 10),
                                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS book_popularities (
                                                 popularity_id BIGSERIAL PRIMARY KEY,
                                                 book_id BIGINT NOT NULL REFERENCES book(id) ON DELETE CASCADE,
                                                 read_count INTEGER NOT NULL DEFAULT 0,
                                                 period VARCHAR(50) NOT NULL,
                                                 calculated_at TIMESTAMP NOT NULL,
                                                 CONSTRAINT uq_book_period UNIQUE (book_id, period)
);

ALTER TABLE loans RENAME COLUMN reader_id TO user_id;
ALTER TABLE loans DROP COLUMN IF EXISTS reader_name;
ALTER TABLE loans DROP COLUMN IF EXISTS book_title;
ALTER TABLE loans ADD CONSTRAINT fk_loans_user FOREIGN KEY (user_id) REFERENCES users(user_id);

ALTER TABLE reservation RENAME COLUMN reader_id TO user_id;
ALTER TABLE reservation DROP CONSTRAINT IF EXISTS fk_reservation_reader;
ALTER TABLE reservation ADD CONSTRAINT fk_reservation_user FOREIGN KEY (user_id) REFERENCES users(user_id);