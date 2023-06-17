package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CalculatorApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testSum() {
		Calculator calculator = new Calculator();
		assert(calculator.sum(1, 1) == 2);
	}

}
