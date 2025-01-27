
-- Create Author Table
CREATE TABLE authors (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Create Book Table
CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    published_date DATE NOT NULL,
    author_id INT NOT NULL,
    FOREIGN KEY (author_id) REFERENCES authors(id) ON DELETE CASCADE
);

-- Insert sample data
INSERT INTO authors (name) VALUES ('J.K. Rowling');
INSERT INTO authors (name) VALUES ('J.R.R. Tolkien');

INSERT INTO books (title, published_date, author_id) VALUES ('Harry Potter', '1997-06-26', 1);
INSERT INTO books (title, published_date, author_id) VALUES ('The Hobbit', '1937-09-21', 2);
