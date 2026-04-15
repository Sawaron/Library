
CREATE TABLE authors
(
    id        BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    biography TEXT
);

CREATE TABLE readers
(
    id        BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email     VARCHAR(100) UNIQUE,
    phone     VARCHAR(20)
);

-- 2. Книги (ссылаются на авторов)
CREATE TABLE books
(
    id        BIGSERIAL PRIMARY KEY,
    title     VARCHAR(255) NOT NULL,
    isbn      VARCHAR(20),
    author_id BIGINT REFERENCES authors (id)
);

-- 3. Операции (ссылаются на книги и читателей)
CREATE TABLE loans
(
    id                 BIGSERIAL PRIMARY KEY,
    book_id            BIGINT NOT NULL REFERENCES books (id),
    reader_id          BIGINT NOT NULL REFERENCES readers (id),
    loan_date          DATE DEFAULT CURRENT_DATE,
    return_date        DATE,
    actual_return_date DATE,
    status             VARCHAR(50)
);
