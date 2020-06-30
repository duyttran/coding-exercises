package com.dtran.other;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FindAllPrimeFibonacci {
    public static boolean isPrime(long num) {
        if (num == 1) {
            return false;
        }
        if (num == 2) {
            return true;
        }
        for (int i = 2; i <= num/2; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static List<Long> fibonacciNumbers(long n) {
        List<Long> fibonacci = new ArrayList<>();
        fibonacci.add(1L);
        fibonacci.add(1L);
        for (int i = 2; i < n; i++) {
            fibonacci.add(fibonacci.get(i - 1) + fibonacci.get(i - 2));
        }
        return fibonacci;
    }

    public static long[] solution(long n) {
        List<Long> fibonacciNumbers = fibonacciNumbers(n);
        List<Long> primeNumbers = fibonacciNumbers.stream()
            .filter(num -> isPrime(num))
            .collect(Collectors.toList());
        long[] primes = new long[primeNumbers.size()];
        for (int i = 0; i < primes.length; i++) {
            primes[i] = primeNumbers.get(i);
        }
        return primes;
    }
}
