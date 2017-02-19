package com.contribe.enums;

import com.contribe.service.BookList;

/**
 * Status that would be returned by the buy method from {@link BookList}
 * 
 * @author abhijeetshiralkar
 *
 */
public enum Status {

	OK(0), NOT_IN_STOCK(1), DOES_NOT_EXIST(2);

	private int value;

	private Status(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
