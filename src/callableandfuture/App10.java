package callableandfuture;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class App10 {
  public static void main(String[] args) {
    ExecutorService executor = Executors.newCachedThreadPool();
    
    Future<Integer> future = executor.submit(new Callable<Integer>() {
      
      @Override
      public Integer call() throws Exception {
        Random rand = new Random();
        int duration = rand.nextInt(4000);
        
        if (duration > 1000) {
          throw new IOException("sleeping for too long.");
        }
        
        System.out.println("Starting...");
        
        try {
          Thread.sleep(duration);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        
        System.out.println("Finished.");
        
        return duration;
      }
    });
    
    executor.shutdown();
    
    try {
      System.out.println("Result is: " + future.get());
    } catch (InterruptedException | ExecutionException e1) {
     IOException ioexc = (IOException) e1.getCause();
      System.out.println(ioexc.getMessage());
    }
  }
}
