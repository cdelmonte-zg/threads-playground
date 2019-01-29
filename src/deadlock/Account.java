package deadlock;

public class Account {
  private int balance = 100000;

  void deposit(int amount) {
    balance += amount;
  }

  void withdraw(int amount) {
    balance -= amount;
  }

  int getBalance() {
    return balance;
  }

  static void transfer(Account acc1, Account acc2, int amount) {
    acc1.withdraw(amount);
    acc2.deposit(amount);
  }
}
