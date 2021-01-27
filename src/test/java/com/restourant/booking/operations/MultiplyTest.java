package com.restourant.booking.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MultiplyTest {

	Multiply multiply = new Multiply();

	@Test
	void multiplyPositives() {
		int resultExpected = Math.multiplyExact(3, 4);
		int resultActual = multiply.multiply(3, 4);
		assertEquals(resultExpected, resultActual);
	}
	
	@Test
	void multiplyNegatives() {
		int resultExpected = Math.multiplyExact(-3, -4);
		int resultActual = multiply.multiply(-3, -4);
		assertEquals(resultExpected, resultActual);
	}
	
	@Test
	void multiplyPositiveAndNegative() {
		int resultExpected = Math.multiplyExact(3, -4);
		int resultActual = multiply.multiply(3, -4);
		assertEquals(resultExpected, resultActual);
	}
	
	@Test
	void multiplyNegativeAndPositive() {
		int resultExpected = Math.multiplyExact(-3, 4);
		int resultActual = multiply.multiply(-3, 4);
		assertEquals(resultExpected, resultActual);
	}
	
	@Test
	void multiplyZero() {
		int resultExpected = Math.multiplyExact(0, 0);
		int resultActual = multiply.multiply(0, 0);
		assertEquals(resultExpected, resultActual);
	}
	
	@Test
	void multiplyInf() {
		int resultExpected = Math.multiplyExact(-3, 4);
		int resultActual = multiply.multiply(-3, 4);
		assertEquals(resultExpected, resultActual);
	}
}