package semaphore;

import java.util.concurrent.Semaphore;

class Connection {

  private static Connection instance = new Connection();
  private int connections = 0;

  private Semaphore sem = new Semaphore(10, true);
  
  private Connection() {
    // Doing nothing
    
  }

  static Connection getInstance() {
    return instance;
  }
  
  void connect() {
    try {
      sem.acquire();
    } catch (InterruptedException e1) {
      e1.printStackTrace();
    }
    
    try {
      doConnect();
    } finally {
      sem.release();
    }
  }

  private void doConnect(){
    synchronized (this) {
      connections++;
      System.out.println("Current connections: " + connections);
    }
    
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    
    synchronized(this) {
      connections--;
    }
  }
}
