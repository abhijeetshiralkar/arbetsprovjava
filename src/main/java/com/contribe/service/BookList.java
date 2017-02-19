package com.contribe.service;

import java.util.Map;

import com.contribe.enums.Status;
import com.contribe.model.Book;

/**
 * Functionality related to book store.
 * 
 * 
 * @author abhijeetshiralkar
 *
 */
public interface BookList {

	/**
	 * Return back all books or based on the searchString (title/author of the
	 * book)
	 * 
	 * @param searchString
	 *            Search string input (Can be title/author of the book)
	 * @return List of books matching the search criteria
	 */
	public Book[] list(String searchString);

	/**
	 * Add a book to the book store
	 * 
	 * @param book
	 *            Book to be added.
	 * @param quantity
	 *            Number of books to be added
	 * @return true if book was added else false
	 */
	public boolean add(Book book, int quantity);

	/**
	 * Adding additional method to add books to cart
	 * 
	 * @param book
	 *            Book to be added.
	 * @param quantity
	 *            Number of copies to be added
	 */
	public void addToCart(Book book, int quantity);

	/**
	 * Perform the purchase of books. I believe this should be like checkout of
	 * cart. Since the StoreHandler knows about the cart, not passing the list
	 * of Books to buy method
	 * 
	 * @return Map containing Book and the corresponding status after purchase
	 */
	public Map<Book, Status> buy();

	/**
	 * Remove content from cart
	 * 
	 * @param book
	 *            Book to be removed from cart
	 * @param quantity
	 */
	void removeFromCart(Book book, int quantity);

}
