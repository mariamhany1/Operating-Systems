/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment1os;

/**
 *
 * @author Hazem Adel
 */

import java.io.FileNotFoundException;

class Producer implements Runnable{
    private dataQueue data;
    private boolean isRunning;
    
    Producer(dataQueue d){
        data = d;
    }
    @Override
    public void run(){
        // call produce method
        int primes = 0;

        // Iterating for every number from 2 to N
        for (int number = 2; number <= data.N; number++) {
            if(!MillerRabin(number))continue;
            System.out.println("Produce number: " + number);
            produce(number);
            primes++;
            // Get maximum prime number
            data.maxPrime = number;
            
        }
        isRunning = false;
        data.setPrimes(primes);
        
        // Stopping the producer
        produce(-1);
    }
    public void produce(int prime){
        while (data.isFull()) {
            try {
                data.waitingFullState();
            } catch (InterruptedException e) {
                break;
            }
        }
        data.add(prime);
        data.notifyEmptyState();
    }


    // Checking if a number is prime or not
    long binpower(long base, long e, long mod) {
        long result = 1;
        base %= mod;
        while (e > 0) {
            if (e % 2 == 1)
                result = (long)result * base % mod;
            base = (long)base * base % mod;
            e >>= 1;
        }
        return result;
    }

    boolean check_composite(long n, long a, long d, long s) {
        long x = binpower(a, d, n);
        if (x == 1 || x == n - 1)
            return false;
        for (int r = 1; r < s; r++) {
            x = (long)x * x % n;
            if (x == n - 1)
                return false;
        }
        return true;
    };
    boolean MillerRabin(long n) { // returns true if n is prime, else returns false.
        if (n < 2)
            return false;

        int r = 0;
        long d = n - 1;
        while ((d & 1) == 0) {
            d >>= 1;
            r++;
        }

        for (long a : new long []{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37}) {
            if (n == a)
                return true;
            if (check_composite(n, a, d, r))
                return false;
        }
        return true;
    }
    
    boolean getIsRunning(){
        return this.isRunning;
    }
}
