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

		// There would be single instance of StoreHandler ever created as it is
		// singleton
		final BookList storeHandler = new StoreHandler();

		// Look for books
		Book[] books = storeHandler.list("Cunning Bastard");
		books = ArrayUtils.addAll(books, storeHandler.list("First Author"));

		// Add this books to cart
		for (Book book : books) {
			storeHandler.addToCart(book, 10);
		}

		// Now do the checkout
		final Map<Book, Status> bookCheckoutStatus = storeHandler.buy();

		bookCheckoutStatus.forEach((book, status) -> {
			log.info("Status of book purchase for " + book.getTitle() + " is: " + status);
		});

	}

}
