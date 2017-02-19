package com.contribe.service;

import java.util.HashMap;
import java.util.Map;

import com.contribe.model.Book;

/**
 * Implementation for shopping cart for the Bookstore
 * 
 * @author abhijeetshiralkar
 *
 */
public class CartServieImpl implements CartService {

	// This map stores Items and also number of items
	private Map<Book, Integer> booksInCart = new HashMap<Book, Integer>();

	@Override
	public void addToCart(final Book book, final Integer quantity) {
		// We are not doing any validation during adding to cart because the
		// buy() method in StoreHandler does the validation. So we do the
		// validation during checkout/final buy
		final Integer existingBookCount = booksInCart.get(book);
		if (existingBookCount != null) {
			booksInCart.put(book, existingBookCount + quantity);
		} else {
			booksInCart.put(book, quantity);
		}
	}

	@Override
	public void removeFromCart(final Book book, final Integer quantity) {
		// If the book already exist in the cart we update the quantity of books
		// else remove the book
		final Integer existingBookCount = booksInCart.get(book);
		if (existingBookCount != null && existingBookCount > 1 && existingBookCount != quantity) {
			booksInCart.put(book, existingBookCount - quantity);
		} else {
			booksInCart.remove(book);
		}
	}

	@Override
	public Map<Book, Integer> getBooksInCart() {
		return booksInCart;
	}
}
