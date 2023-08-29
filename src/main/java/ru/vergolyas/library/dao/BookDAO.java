package ru.vergolyas.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.vergolyas.library.models.Book;
import ru.vergolyas.library.models.Person;

import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book", new BookMapper());
    }

    public Book show(int bookId) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?", new Object[]{bookId}, new int[]{Types.INTEGER},
                new BookMapper()).stream().findAny().orElse(null);
    }

    public Optional<Book> show(String name) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE name=?", new Object[]{name}, new int[]{Types.VARCHAR},
                new BookMapper()).stream().findAny();
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(name, author, year) VALUES (?, ?, ?)", book.getName(),
                book.getAuthor(), book.getYear());
    }

    public void setOwner(int bookId, int personId) {
        jdbcTemplate.update("UPDATE Book SET Person_id=? WHERE book_id=?", personId, bookId);
    }

    public void removeOwner(int bookId) {
        jdbcTemplate.update("UPDATE Book SET Person_id=null WHERE book_id=?", bookId);
    }

    public Person getOwner(int bookId) {
        return jdbcTemplate.query("SELECT Person.person_id, full_name, year_of_birth " +
                "FROM Person JOIN Book on Person.person_id = Book.person_id " +
                "WHERE book_id=?", new Object[]{bookId}, new int[]{Types.INTEGER}, new PersonMapper()).
                stream().findAny().orElse(null);
    }

    public void delete(int bookId) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", bookId);
    }

    public void update(int bookId, Book updatedBook) {
        jdbcTemplate.update("UPDATE Book SET Name=?, Author=?, Year=? WHERE Book_id=?",
                updatedBook.getName(), updatedBook.getAuthor(), updatedBook.getYear(), bookId);
    }
}
