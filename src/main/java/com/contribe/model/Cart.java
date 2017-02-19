package com.contribe.model;

import java.util.Map;

/**
 * Shopping cart for bookstore
 * 
 * @author abhijeetshiralkar
 *
 */
public interface Cart {

	public void add(Book book, Integer quantity);

	public void remove(Book book, Integer quantity);

	public Map<Book, Integer> getBooksInCart();

}
