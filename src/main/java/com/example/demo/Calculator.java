package com.example.demo;

import org.springframework.stereotype.Service;

/**
 * @author shenhh
 * @created 2023/06/17 16:06
 */

@Service
public class Calculator {
    int sum(int a, int b) {
        return a + b;
    }
}
