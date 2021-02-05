package hu.springbootrestfuljpa.books.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Review {

	@Id
	@GeneratedValue
	private Integer id;

	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Book book;

	public Integer getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setBook(Book book) {
		this.book = book;
	}
}
