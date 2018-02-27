package project;

import project.Repository.BookDetailRepository;
import project.model.*;
import project.Repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class HelloJpaApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(HelloJpaApplication.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookDetailRepository bookDetailRepository;

    public static void main(String[] args) {
        SpringApplication.run(HelloJpaApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        // save a couple of books

        //BookDetail bd = new BookDetail(12);
        //bookDetailRepository.save(bd);

        Book book = new Book("Book A", new BookDetail(16));
        bookRepository.save(book);

       /* book = new Book("Book B", new BookDetail(124));
        bookRepository.save(book);
        book = new Book("Book C", new BookDetail(332));
        bookRepository.save(book);
*/

        // fetch all books
        for (Book b : bookRepository.findAll()) {
            logger.info(b.toString());
        }
    }
}
