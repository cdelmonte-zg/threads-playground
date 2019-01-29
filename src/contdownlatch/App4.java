package contdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App4 {
  public static void main(String[] args) {
    CountDownLatch latch = new CountDownLatch(3);
    
    ExecutorService executor = Executors.newFixedThreadPool(5);
    
    for (int i = 0; i < 5; i++) {
      executor.submit(new Processor(latch));
    }
    
    try {
      latch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    
    System.out.println("Completed.");
  }
}
