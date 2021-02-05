package hu.springbootrestfuljpa.books.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hu.springbootrestfuljpa.books.model.Book;
import hu.springbootrestfuljpa.books.model.Review;
import hu.springbootrestfuljpa.books.repository.BookRepository;
import hu.springbootrestfuljpa.books.repository.ReviewRepository;

@RestController
public class ReviewController {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@GetMapping(path = "/books/{id}/reviews")
	public ResponseEntity<List<Review>> retrieveAllReviews(@PathVariable int id) {
		Optional<Book> book = bookRepository.findById(id);
		if (book.isPresent()) {
			return ResponseEntity.ok(book.get().getReviews());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/books/{id}/reviews")
	public ResponseEntity<Object> createReview(@PathVariable int id, @RequestBody Review review) {
		Optional<Book> bookOptional = bookRepository.findById(id);

		if (!bookOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Book book = bookOptional.get();
		review.setBook(book);

		return ResponseEntity.ok(reviewRepository.save(review));
	}

}
