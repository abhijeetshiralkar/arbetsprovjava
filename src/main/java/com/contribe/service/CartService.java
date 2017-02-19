package com.contribe.service;

import java.util.Map;

import com.contribe.model.Book;

/**
 * Shopping cart for bookstore
 * 
 * @author abhijeetshiralkar
 *
 */
public interface CartService {

	public void addToCart(Book book, Integer quantity);

	public void removeFromCart(Book book, Integer quantity);

	public Map<Book, Integer> getBooksInCart();

}
