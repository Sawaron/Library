SET search_path TO public;

CREATE TABLE author (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(255),
                        lastname VARCHAR(255),
                        birth_date DATE,
                        death_date DATE
);

CREATE TABLE book (
                      id BIGSERIAL PRIMARY KEY,
                      title VARCHAR(255) NOT NULL,
                      author_id BIGINT,
                      genre VARCHAR(255) NOT NULL,
                      isbn VARCHAR(255) NOT NULL UNIQUE,
                      summary VARCHAR(1000),
                      status VARCHAR(50),
                      created_at TIMESTAMP,
                      updated_at TIMESTAMP,
                      CONSTRAINT fk_book_author
                          FOREIGN KEY (author_id)
                              REFERENCES author(id)
);

CREATE TABLE loans (
                       id BIGSERIAL PRIMARY KEY,
                       book_id BIGINT,
                       book_title VARCHAR(255),
                       reader_id BIGINT,
                       reader_name VARCHAR(255),
                       loan_date DATE,
                       return_date DATE,
                       actual_return_date DATE,
                       status VARCHAR(50)
);

CREATE TABLE reader (
                        reader_id BIGSERIAL PRIMARY KEY,
                        reader_name VARCHAR(255) NOT NULL,
                        reader_lastname VARCHAR(255) NOT NULL,
                        reader_phone VARCHAR(50),
                        reader_address VARCHAR(255),
                        reader_status VARCHAR(50),
                        reader_registration_date DATE,
                        reader_email VARCHAR(255) UNIQUE
);

CREATE TABLE reservation (
                                     id BIGSERIAL PRIMARY KEY,
                                     book_id BIGINT NOT NULL,
                                     reader_id BIGINT NOT NULL,
                                     reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     status VARCHAR(50) NOT NULL,
                                     CONSTRAINT fk_reservation_book FOREIGN KEY (book_id) REFERENCES library.book(id),
                                     CONSTRAINT fk_reservation_reader FOREIGN KEY (reader_id) REFERENCES library.reader(reader_id)
);