package com.contribe.service;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.contribe.model.Book;

/**
 * Unit test for CartServiceImpl
 * 
 * @author abhijeetshiralkar
 *
 */
public class CartServiceTest {

	private static CartService cartService;

	@BeforeClass
	public static void setUp() {
		cartService = new CartServieImpl();
	}

	@Test
	public void testAddToCart() {
		Book book = new Book("new_book", "new_author", BigDecimal.valueOf(20));
		Assert.assertNull(cartService.getBooksInCart().get(book));
		cartService.addToCart(book, 10);
		Assert.assertTrue(cartService.getBooksInCart().containsKey(book));
	}

	@Test
	public void testRemoveFromCart() {
		Book book = new Book("add_book", "add_author", BigDecimal.valueOf(20));
		cartService.addToCart(book, 10);
		Assert.assertTrue(cartService.getBooksInCart().containsKey(book));
		cartService.removeFromCart(book, 10);
		Assert.assertFalse(cartService.getBooksInCart().containsKey(book));
	}

}
