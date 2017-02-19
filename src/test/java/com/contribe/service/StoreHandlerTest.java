package com.contribe.service;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.contribe.enums.Status;
import com.contribe.model.Book;

import junit.framework.Assert;

/**
 * Unit test class for StoreHandler
 * 
 * @author user
 *
 */
public class StoreHandlerTest {

	private static StoreHandler storeHandler;

	@BeforeClass
	public static void setUp() {
		storeHandler = new StoreHandler();
	}

	/**
	 * Test if the list by author functionality works fine
	 */
	@Test
	public void testListByAuthor() {
		final Book[] books = storeHandler.list("Cunning Bastard");
		for (Book book : books) {
			Assert.assertEquals("Cunning Bastard", book.getAuthor());
		}
	}

	/**
	 * Test if add to store works fine
	 */
	@Test
	public void testAddToStore() {
		// Verify that there are no matching books before we add
		Assert.assertTrue(storeHandler.list("testing_author").length == 0);

		final Book book = new Book("testing_title", "testing_author", BigDecimal.TEN);
		final boolean added = storeHandler.add(book, 10);
		final Book[] books = storeHandler.list("testing_author");

		// Assert that we get one such record after we add it to bookstore
		Assert.assertTrue(storeHandler.list("testing_author").length == 1);
	}

	/**
	 * Test the scenario when we add to cart and purchase was successful
	 */
	@Test
	public void testBuySuccessful() {
		Book bookForTest = new Book("Generic Title", "First Author", BigDecimal.valueOf(185.50));
		storeHandler.addToCart(bookForTest, 2);
		Map<Book, Status> status = storeHandler.buy();
		Assert.assertEquals(Status.OK, status.get(bookForTest));
	}

	/**
	 * Test the scenario when we add to cart and the book was NOT_IN_STOCK
	 */
	@Test
	public void testBuyNotInStock() {
		Book bookForTest = new Book("Generic Title", "First Author", BigDecimal.valueOf(185.50));
		storeHandler.addToCart(bookForTest, 5);
		Map<Book, Status> status = storeHandler.buy();
		Assert.assertEquals(Status.NOT_IN_STOCK, status.get(bookForTest));
	}

	/**
	 * Test the scenario when we add to cart and the book DOES_NOT_EXIST
	 */
	@Test
	public void testBuyDoesNotExist() {
		Book bookForTest = new Book("Does_NOT_EXIST", "First Author", BigDecimal.valueOf(185.50));
		storeHandler.addToCart(bookForTest, 5);
		Map<Book, Status> status = storeHandler.buy();
		Assert.assertEquals(Status.DOES_NOT_EXIST, status.get(bookForTest));
	}

}
