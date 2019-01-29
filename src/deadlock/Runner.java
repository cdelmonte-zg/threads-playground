package deadlock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


class Runner {
  private Account acc1 = new Account();
  private Account acc2 = new Account();

  private Lock lock1 = new ReentrantLock();
  private Lock lock2 = new ReentrantLock();

  private void acquireLock(Lock lock1, Lock lock2) throws InterruptedException {
    while (true) {

      // acquire locks
      boolean gotFirstLock = false;
      boolean gotSecondLock = false;

      try {
        gotFirstLock = lock1.tryLock();
        gotSecondLock = lock2.tryLock();
      } finally {
        if (gotFirstLock && gotSecondLock) {
          return;
        }
        
        if (gotFirstLock) {
          lock1.unlock();
        }
        
        if (gotSecondLock) {
          lock2.unlock();
        }
      }

      // locks not acquired
      Thread.sleep(1);
    }
  }


  void firstThread() throws InterruptedException {
    Random rand = new Random();
    for (int i = 0; i < 10000; i++) {
      acquireLock(lock1, lock2);

      try {
        Account.transfer(acc1, acc2, rand.nextInt(100));
      } finally {
        lock1.unlock();
        lock2.unlock();
      }
    }
  }

  void secondThread() throws InterruptedException {
    Random rand = new Random();

    for (int i = 0; i < 10000; i++) {
      acquireLock(lock1, lock2);

      try {
        Account.transfer(acc2, acc1, rand.nextInt(100));
      } finally {
        lock2.unlock();
        lock1.unlock();
      }
    }
  }

  void finished() {
    System.out.println("Account 1 balance: " + acc1.getBalance());
    System.out.println("Account 2 balance: " + acc2.getBalance());
    System.out.println("Total balance: " + (acc1.getBalance() + acc2.getBalance()));
  }
}
