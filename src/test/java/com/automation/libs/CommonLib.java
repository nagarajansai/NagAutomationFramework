package com.automation.libs;

import java.util.Random;

import com.automation.webaccelerators.ActionEngine;

public class CommonLib extends ActionEngine {
	public int generateRandomNumber() throws Throwable {

		Random generator = new Random();
		int intRandom_number = generator.nextInt(9999) + 1000;

		return intRandom_number;
	}

}
