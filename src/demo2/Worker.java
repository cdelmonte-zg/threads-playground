package demo2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Worker {
  private Random random = new Random();

  private Object lock1 = new Object();
  private Object lock2 = new Object();

  // you can complicate things if you try to synchronise the object,
  // you want to write in (for ex. the lists). That's the reason,
  // why other extra lock objects are introduced;
  private List<Integer> list1 = new ArrayList<>();
  private List<Integer> list2 = new ArrayList<>();

  void stageOne() {
    synchronized (lock1) {
      try {
        Thread.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      list1.add(random.nextInt(100));
    }
  }

  void stageTwo() {
    synchronized (lock2) {
      try {
        Thread.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      list2.add(random.nextInt(100));
    }
  }

  void process() {
    for (int i = 0; i < 1000; i++) {
      stageOne();
      stageTwo();
    }
  }

  void main() {
    System.out.println("Hello");

    Long start = System.currentTimeMillis();

    Thread t1 = new Thread(new Runnable() {
      @Override
      public void run() {
        process();
      }
    });

    t1.start();


    Thread t2 = new Thread(new Runnable() {
      @Override
      public void run() {
        process();
      }
    });

    t2.start();


    try {
      t1.join();
      t2.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    Long end = System.currentTimeMillis();

    System.out.println("Time take: " + (end - start));
    System.out.println("List1: " + list1.size() + "; List2: " + list2.size());

  }
}
