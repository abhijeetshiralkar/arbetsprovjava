package com.contribe.main;

import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import com.contribe.enums.Status;
import com.contribe.model.Book;
import com.contribe.service.BookList;
import com.contribe.service.StoreHandler;

/**
 * Main program for the BookStore
 * 
 * @author user
 *
 */
public class BookStoreMain {

	public static void main(final String a[]) {

		final Logger log = Logger.getLogger(BookStoreMain.class);

		// Create instance of StoreHandler. The StoreHandler would also create
		// instance of cartservice. We would add books to the cart and finally
		// checkout the
		// cart in
		// buy method
		final BookList storeHandler = new StoreHandler();

		// Look for books based on the author
		Book[] books = storeHandler.list("Cunning Bastard");
		books = ArrayUtils.addAll(books, storeHandler.list("First Author"));

		// Add this books to cart
		for (Book book : books) {
			storeHandler.addToCart(book, 10);
		}

		// Now do the checkout
		final Map<Book, Status> bookCheckoutStatus = storeHandler.buy();

		// The status for the book purchase is as below
		bookCheckoutStatus.forEach((book, status) -> {
			log.info("Status of book purchase for " + book.getTitle() + " is: " + status);
		});

	}

}
