package com.contribe.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.contribe.enums.Status;
import com.contribe.model.Book;

/**
 * Store implementation for Books
 * 
 * @author abhijeetshiralkar
 *
 */
public class StoreHandler implements BookList {

	private static final Logger logger = Logger.getLogger(StoreHandler.class);

	// This map stores books and also number of books
	private static Map<Book, Integer> booksInStore = new HashMap<Book, Integer>();

	private CartService cartService;

	public StoreHandler() {
		cartService = new CartServieImpl();
	}

	/**
	 * Initialize the Items list in the StoreHandler. Placed in a static block
	 * as want this to be executed only once
	 */
	static {
		try {
			final URL url = new URL("http://www.contribe.se/bookstoredata/bookstoredata.txt");
			final BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(url.openConnection().getInputStream()));
			bufferedReader.lines().forEach(bookInputString -> {
				final String[] bookInput = bookInputString.split(";");

				BigDecimal price = BigDecimal.ZERO;

				if (StringUtils.isNotEmpty(bookInput[2])) {
					price = BigDecimal.valueOf(Double.parseDouble(bookInput[2].replaceAll(",", "")));
				}

				final Book book = new Book(bookInput[0], bookInput[1], price);

				booksInStore.put(book, Integer.parseInt(bookInput[3]));
			});
		} catch (MalformedURLException e) {
			logger.warn("Could not get the initial book store data", e);
			// No use in proceeding if we dont have the data
			System.exit(1);
		} catch (IOException e) {
			logger.warn("Could not get the initial book store data", e);
			// No use in proceeding if we dont have the data
			System.exit(1);
		}
	}

	@Override
	public boolean add(final Book book, final int quantity) {
		final Integer existingBookCount = booksInStore.get(book);
		if (existingBookCount != null) {
			booksInStore.put(book, existingBookCount + quantity);
		} else {
			booksInStore.put(book, quantity);
		}

		return true;
	}

	@Override
	public Map<Book, Status> buy() {
		// Map of result
		Map<Book, Status> result = new HashMap<Book, Status>();

		// Books to be bought are maintained in the cart
		Map<Book, Integer> booksInCart = cartService.getBooksInCart();

		BigDecimal totalPrice = BigDecimal.ZERO;
		for (Book book : booksInCart.keySet()) {
			// If the bookStore doesn't contain the book
			if (!booksInStore.containsKey(book)) {
				result.put(book, Status.DOES_NOT_EXIST);
				logger.info("Purchase for book " + book.getTitle()
						+ " was not successful as book does not exist in book store");
				continue;
			}
			// If bookstore contains the book but is out of stock
			if (booksInStore.containsKey(book) && booksInCart.get(book) > booksInStore.get(book)) {
				logger.info("Purchase for book " + book.getTitle() + " was not successful as book is out of stock");
				result.put(book, Status.NOT_IN_STOCK);
				continue;
			}

			// If bookstore contains the book and is in stock
			if (booksInStore.containsKey(book) && booksInCart.get(book) <= booksInStore.get(book)) {
				result.put(book, Status.OK);
				final Integer quantityOfBook = booksInCart.get(book);
				final BigDecimal price = book.getPrice().multiply(BigDecimal.valueOf(quantityOfBook));
				totalPrice = totalPrice.add(price);

				logger.info("Purchase for book " + book.getTitle() + " is successful. The price of these books is: "
						+ price);

				// Update the books in store now that the checkout/buy was
				// successful
				final Integer quantityInStore = booksInStore.get(book);
				final Integer resultantQuantity = quantityInStore - booksInCart.get(book);
				// If the resultantQuantity is 0 remove the book from book store
				// else update the count
				if (resultantQuantity == 0) {
					booksInStore.remove(book);
				} else {
					booksInStore.put(book, resultantQuantity);
				}
			}
		}

		logger.info("Total price for the books purchased is " + totalPrice);
		// Clear the contents of the cart at this stage
		cartService.getBooksInCart().clear();
		return result;
	}

	@Override
	public Book[] list(final String searchString) {
		Set<Book> books = new HashSet<Book>();

		if (StringUtils.isEmpty(searchString)) {
			books = booksInStore.keySet();
		} else {
			books = booksInStore.keySet().stream().filter(book -> book.getAuthor().equalsIgnoreCase(searchString)
					|| book.getTitle().equalsIgnoreCase(searchString)).collect(Collectors.toSet());
		}
		return books.toArray(new Book[books.size()]);
	}

	@Override
	public void addToCart(final Book book, final int quantity) {
		cartService.addToCart(book, quantity);
	}

	@Override
	public void removeFromCart(final Book book, final int quantity) {
		cartService.removeFromCart(book, quantity);
	}

	public static Map<Book, Integer> getBooksInStore() {
		return booksInStore;
	}

}
