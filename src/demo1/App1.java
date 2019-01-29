package demo1;

public class App1 {
  private int count;
  
  public static void main(String[] args) {
    App1 app = new App1();
    
    app.doWork();
  }

  synchronized void increment() {
    count++;
  }
  
  void doWork() {
    Thread t1 = new Thread(new Runnable() {
      @Override
      public void run() {
        for (int i = 0; i < 1000 ; i++) {
          increment();
        }
      }
    });
    
    Thread t2 = new Thread(new Runnable() {
      @Override
      public void run() {
        for (int i = 0; i < 1000 ; i++) {
          increment();
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
    
    System.out.println("Count is: " + count);
  }
  
}
