package threadpools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App3 {
  public static void main(String[] args) {
    ExecutorService executor = Executors.newFixedThreadPool(2);
    
    for (int i = 1; i < 6; i++) {
      executor.submit(new Processor(i));
    }
    
    executor.shutdown();
    
    System.out.println("All tasks submitted.");
    
    try {
      executor.awaitTermination(1, TimeUnit.DAYS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    
    System.out.println("All tasks completed.");
  }
}
