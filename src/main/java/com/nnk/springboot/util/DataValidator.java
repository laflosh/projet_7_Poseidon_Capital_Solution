package com.nnk.springboot.util;

import org.springframework.stereotype.Component;

/**
 * Methods for checking data of an object
 */
@Component
public class DataValidator {

	/**
	 * Check if the data is a String
	 *
	 * @param data
	 * @return
	 */
	public boolean checkString(Object data) {

		return data instanceof String;

	}

	/**
	 * Check if data is an Integer
	 *
	 * @param data
	 * @return
	 */
	public boolean checkInteger(Object data) {

		return data instanceof Integer;

	}

	/**
	 * Check if the data is a Double
	 *
	 * @param data
	 * @return
	 */
	public boolean checkDouble(String data) {

		try {

			Double.parseDouble(data);
			return true;

		} catch (NumberFormatException e ) {

			return false;

		}

	}

}
