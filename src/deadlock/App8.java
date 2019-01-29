package deadlock;

public class App8 {
  public static void main(String[] args) {
    Runner runner = new Runner();
    
    Thread t1 = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          runner.firstThread();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
    
    
    Thread t2 = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          runner.secondThread();  
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
    
    t1.start();
    t2.start();
    
    try {
      t1.join();
      t2.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    
    runner.finished();
  }
}
