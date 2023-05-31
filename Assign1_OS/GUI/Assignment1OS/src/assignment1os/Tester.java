/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment1os;

/**
 *
 * @author Hazem Adel
 */
import java.util.StringTokenizer;
import java.io.*;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
public class Tester {
    public int mx, count, N;
    Tester(int N, int mx, int count){
        this.mx = mx;
        this.count = count;
        this.N = N;
    }
    static class FastScanner {
        BufferedReader in;
        StringTokenizer st;

        public FastScanner() {
            try {
                this.in = new BufferedReader(new FileReader("primes.txt"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        public String nextToken() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(in.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(nextToken());
        }

        public long nextLong() {
            return Long.parseLong(nextToken());
        }

    }
    boolean test() throws FileNotFoundException {

        File myObj = new File("primes.txt");
        Scanner myReader = new Scanner(myObj);
        int n, max = 0, cnt = 0;
        while (true){
            n = myReader.nextInt();
            if(n == -1 || n > N)break;
            max = n;
            cnt++;
        }
        return max == this.mx && cnt == this.count;
    }
}

