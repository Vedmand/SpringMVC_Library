package ru.vergolyas.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.vergolyas.library.models.Book;
import ru.vergolyas.library.models.Person;

import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new PersonMapper());
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE person_id=?", new Object[]{id}, new int[]{
            Types.INTEGER}, new PersonMapper()).stream().findAny().orElse(null);
    }

    public Optional<Person> show(String fullName) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE full_name=?", new Object[]{fullName}, new int[]{
                Types.VARCHAR}, new PersonMapper()).stream().findAny();
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(full_name, year_of_birth) VALUES (?, ?)", person.getFullName(),
                person.getYearOfBirth());
    }

    public List<Book> getBooks(int id) {
        return jdbcTemplate.query("SELECT book_id, name, author, year, Book.person_id " +
                "FROM Person JOIN Book on Person.person_id = Book.person_id " +
                "WHERE Person.person_id=?", new Object[]{id}, new int[]{Types.INTEGER}, new BookMapper());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET full_name=?, year_of_birth=? WHERE person_id=?",
                updatedPerson.getFullName(), updatedPerson.getYearOfBirth(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE person_id=?", id);
    }

}
