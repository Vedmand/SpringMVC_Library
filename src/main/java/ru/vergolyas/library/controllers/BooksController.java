package ru.vergolyas.library.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vergolyas.library.dao.BookDAO;
import ru.vergolyas.library.dao.PersonDAO;
import ru.vergolyas.library.models.Book;
import ru.vergolyas.library.models.Person;
import ru.vergolyas.library.util.BookValidator;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    private final BookValidator bookValidator;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());

        return "/books/index";
    }

    @GetMapping("/{bookId}")
    public String show(@PathVariable("bookId") int bookId, Model model, @ModelAttribute("person") Person person) {
        Book book = bookDAO.show(bookId);
        model.addAttribute("book", book);
        model.addAttribute("people", personDAO.index());
        model.addAttribute("owner", bookDAO.getOwner(bookId));

        return "/books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "/books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book")  @Valid Book book,
                         BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) return "/books/new";

        bookDAO.save(book);

        return "redirect:/books";
    }

    @PatchMapping("/appointment/{bookId}")
    public String bookAppointment(@ModelAttribute("person") Person person, @PathVariable("bookId") int bookId) {
        bookDAO.setOwner(bookId, person.getPersonId());

        return "redirect:/books/" + bookId;
    }

    @PatchMapping("/release/{bookId}")
    public String releaseBook(@PathVariable("bookId") int bookId) {
        bookDAO.removeOwner(bookId);

        return "redirect:/books/" + bookId;
    }

    @DeleteMapping("{bookId}")
    public String delete(@PathVariable("bookId") int bookId) {
        bookDAO.delete(bookId);

        return "redirect:/books";
    }

    @GetMapping("/edit/{bookId}")
    public String edit(@PathVariable("bookId") int bookId, Model model) {
        model.addAttribute("book", bookDAO.show(bookId));

        return "/books/edit";
    }

    @PatchMapping("/edit/{bookId}")
    public String update(@PathVariable("bookId") int bookId, @ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        //bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) return "books/edit";

        bookDAO.update(bookId, book);

        return "redirect:/books";
    }
}
