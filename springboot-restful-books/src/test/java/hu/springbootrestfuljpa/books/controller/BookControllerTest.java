package hu.springbootrestfuljpa.books.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import hu.springbootrestfuljpa.books.model.Book;
import hu.springbootrestfuljpa.books.model.Review;
import hu.springbootrestfuljpa.books.repository.BookRepository;
import hu.springbootrestfuljpa.books.repository.ReviewRepository;

public class BookControllerTest {
	
	private static final Book BOOK = new Book();
	private static final Book BOOK_EMPTY = new Book();
	private static final Optional<Book> OPTIONAL_BOOK = Optional.of(BOOK);
	private static final Optional<Book> OPTIONAL_BOOK_EMPTY = Optional.empty();
	
	private static int ID_BOOK = 1;
	private static final String AUTHOR = "Homero";
	private static final String TITLE = "La Odisea";
	private static int RELEASE = 1;
	private static List<Review> REVIEWS = new ArrayList<>();
	
	private static final Review REVIEW = new Review();
	private static final String DESCRIPTION = "Bueno";
	private static final int ID_REVIEW = 1;
	
	@Mock
	BookRepository bookRepository;
	
	@InjectMocks
	BookController bookController;
	
	@Mock
	ReviewRepository reviewRepository;
	
	@InjectMocks
	ReviewController reviewController;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		
		BOOK.setAuthor(AUTHOR);
		BOOK.setId(ID_BOOK);
		BOOK.setRelease(RELEASE);
		BOOK.setReviews(REVIEWS);
		BOOK.setTitle(TITLE);
		
		REVIEW.setBook(BOOK);
		REVIEW.setDescription(DESCRIPTION);
		REVIEW.setId(ID_REVIEW);
	}
	
	@Test
	public void retrieveAllBooksTest() {
		
		Mockito.when(bookRepository.findAll()).thenReturn(Arrays.asList(BOOK));
		
		List<Book> booksExpected = bookController.retrieveAllBooks();
		assertNotNull(booksExpected);
		assertFalse(booksExpected.isEmpty());
	}
	
	@Test
	public void retrieveBookTest() {
		Mockito.when(bookRepository.findById(ID_BOOK)).thenReturn(OPTIONAL_BOOK);
		ResponseEntity<Book> responseBook = bookController.retrieveBook(ID_BOOK);
		assertEquals(AUTHOR, responseBook.getBody().getAuthor());
		assertEquals(ID_BOOK, responseBook.getBody().getId());
		assertEquals(RELEASE, responseBook.getBody().getRelease());
		assertEquals(REVIEWS, responseBook.getBody().getReviews());
		assertEquals(TITLE, responseBook.getBody().getTitle());
		assertEquals(HttpStatus.OK, responseBook.getStatusCode());
	}
	
	@Test
	public void retrieveBookNotFoundTest() {
		Mockito.when(bookRepository.findById(ID_BOOK)).thenReturn(OPTIONAL_BOOK_EMPTY);
		ResponseEntity<Book> responseBook = bookController.retrieveBook(ID_BOOK);
		assertEquals(HttpStatus.NOT_FOUND, responseBook.getStatusCode());
	}
	
	@Test
	public void createBookTest() {
		Mockito.when(bookRepository.save(BOOK)).thenReturn(BOOK);
		Mockito.when(bookRepository.existsById(ID_BOOK)).thenReturn(false);
		ResponseEntity<Object> responseBook = bookController.createBook(BOOK);
		assertNotNull(responseBook.getBody());
		assertEquals(HttpStatus.OK, responseBook.getStatusCode());
	}
	
	@Test
	public void createBookConflictTest() {
		Mockito.when(bookRepository.existsById(ID_BOOK)).thenReturn(true);
		ResponseEntity<Object> responseBook = bookController.createBook(BOOK);
		assertNull(responseBook.getBody());
		assertEquals(HttpStatus.CONFLICT, responseBook.getStatusCode());
	}
	
	@Test
	public void createBookNullIdTest() {
		Mockito.when(bookRepository.existsById(null)).thenReturn(false);
		ResponseEntity<Object> responseBook = bookController.createBook(BOOK_EMPTY);
		assertNull(responseBook.getBody());
		assertEquals(HttpStatus.OK, responseBook.getStatusCode());
	}
	
	@Test
	public void createBookExistIdTest() {
		Mockito.when(bookRepository.existsById(ID_BOOK)).thenReturn(true);
		ResponseEntity<Object> responseBook = bookController.createBook(BOOK_EMPTY);
		assertNull(responseBook.getBody());
		assertEquals(HttpStatus.OK, responseBook.getStatusCode());
	}
	
	@Test
	public void deleteBookTest() {
		Mockito.when(bookRepository.findById(ID_BOOK)).thenReturn(OPTIONAL_BOOK);
		ResponseEntity<Object> responseBook = bookController.deleteBook(ID_BOOK);
		assertNull(responseBook.getBody());
		assertEquals(HttpStatus.OK, responseBook.getStatusCode());
	}
	
	@Test
	public void deleteBookNotPresentTest() {
		Mockito.when(bookRepository.findById(ID_BOOK)).thenReturn(OPTIONAL_BOOK_EMPTY);
		ResponseEntity<Object> responseBook = bookController.deleteBook(ID_BOOK);
		assertEquals(HttpStatus.NOT_FOUND, responseBook.getStatusCode());
	}
	
	@Test
	public void retrieveAllReviewsTest() {
		Mockito.when(bookRepository.findById(ID_BOOK)).thenReturn(OPTIONAL_BOOK);
		ResponseEntity<List<Review>> reviewsResponse = reviewController.retrieveAllReviews(ID_BOOK);
		assertNotNull(reviewsResponse.getBody());
		assertEquals(HttpStatus.OK, reviewsResponse.getStatusCode());
		assertSame(ArrayList.class, reviewsResponse.getBody().getClass());
	}
	
	@Test
	public void retrieveAllReviewsNotPresentTest() {
		Mockito.when(bookRepository.findById(ID_BOOK)).thenReturn(OPTIONAL_BOOK_EMPTY);
		ResponseEntity<List<Review>> reviewsResponse = reviewController.retrieveAllReviews(ID_BOOK);
		assertNull(reviewsResponse.getBody());
		assertEquals(HttpStatus.NOT_FOUND, reviewsResponse.getStatusCode());
	}
	
	@Test
	public void createReviewTest() {
		Mockito.when(bookRepository.findById(ID_BOOK)).thenReturn(OPTIONAL_BOOK);
		Mockito.when(reviewRepository.save(REVIEW)).thenReturn(REVIEW);
		ResponseEntity<Object> newReviewResponse = reviewController.createReview(ID_BOOK, REVIEW);
		assertNotNull(newReviewResponse.getBody());
		assertEquals(HttpStatus.OK, newReviewResponse.getStatusCode());
	}
	
	@Test
	public void createReviewNotFoundTest() {
		Mockito.when(bookRepository.findById(ID_BOOK)).thenReturn(OPTIONAL_BOOK_EMPTY);
		ResponseEntity<Object> newReviewResponse = reviewController.createReview(ID_BOOK, REVIEW);
		assertNull(newReviewResponse.getBody());
		assertEquals(HttpStatus.NOT_FOUND, newReviewResponse.getStatusCode());
	}
	
}
