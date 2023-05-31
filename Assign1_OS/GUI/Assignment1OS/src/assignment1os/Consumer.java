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
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

class Consumer implements Runnable{
    dataQueue data;
    FileWriter out;
    private volatile boolean isRunning;
    private int counter;
    
    Consumer(dataQueue d, String outputFileName)throws IOException {
        data = d;
        isRunning = true;
        counter = 0;
        try {
            out = new FileWriter(outputFileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run(){
        try {
            consume();
        } catch (IOException ex) {
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Stopping consumer thread
    public void stop() {
        isRunning = false;
        data.notifyEmptyState();
    }
    public void consume()throws IOException{
        while (isRunning){

            if (data.isEmpty()) {
                try {
                    data.waitEmptyState();
                } catch (InterruptedException e) {
                    break;
                }
            }

            int prime = data.remove();
            
            // Checking if there are no primes to consume and terminate
            if(prime == -1){
                stop();
                out.close();
                Tester tester = new Tester(data.N, data.getMaxPrime(), data.getNumOfPrimes());
                boolean result = false;
                try {
                    result = tester.test();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                if(result){
                    System.out.println("PASSED\n");
                }
                else{
                    System.out.println("WRONG\n");
                }
                return;
            }
            System.out.println("Consume number: " + prime);
            if(counter > 0) out.write(", ");
            data.notifyFullState();
            try {
                out.write("\""+Integer.toString(prime)+"\"");
                counter++;
            } catch (IOException ex) {
                Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
