package com.vikash.spring.mongo.api.resource;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vikash.spring.mongo.api.model.Book;
import com.vikash.spring.mongo.api.repository.BookRepository;

@RestController
public class BookController {
	public static final Logger logger = LoggerFactory.getLogger(BookController.class);
	@Autowired
	private BookRepository repository;

	@PostMapping("/addBook")
	public String saveBook(@RequestBody Book book) {
		repository.save(book);
		return "Added book with id : " + book.getId();
	}

	@GetMapping("/findAllBooks")
	public List<Book> getBooks() {
		return repository.findAll();
	}

	@GetMapping("/findAllBooks/{id}")
	public Optional<Book> getBook(@PathVariable int id) {
		return repository.findById(id);
	}
@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody Book book) {
		Optional<Book> b1 = repository.findById(id);
		Book currentBook = b1.get();
		if (currentBook == null) {
			logger.error("Unable to update. Book with id {} not found.", id);
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}

		currentBook.setAuthorName(book.getAuthorName());
		currentBook.setBookName(book.getBookName());
		currentBook.setId(book.getId());

		repository.save(currentBook);

		return new ResponseEntity<Book>(currentBook, HttpStatus.OK);
	}

}
