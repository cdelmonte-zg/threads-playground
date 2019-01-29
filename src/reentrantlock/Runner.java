package reentrantlock;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


class Runner {

  private int count = 0;
  private Lock lock = new ReentrantLock();
  private Condition cond = lock.newCondition();

  void increment() {
    for (int i = 0; i < 10000; i++) {
      count++;
    }
  }

  void firstThread() throws InterruptedException {
    lock.lock();
    
    System.out.println("Waiting...");
    cond.await();
    
    try {
      increment();
    } finally {
      lock.unlock();
    }
  }

  void secondThread() throws InterruptedException {
    Thread.sleep(1000);
    lock.lock();
    
    System.out.println("Press the return key!");
    new Scanner(System.in).nextLine();
    
    System.out.println("Got return key.");
    cond.signal();
    
    try {
      increment();
    } finally {
      lock.unlock();
    }
  }

  void finished() {
    System.out.println("Count is: " + count);
  }
}
