import java.io.*;
import java.util.*;

class Account implements Serializable {
    int accNo;
    String name;
    double balance;

    public Account(int accNo, String name, double balance) {
        this.accNo = accNo;
        this.name = name;
        this.balance = balance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Amount Deposited Successfully!");
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient Balance!");
        } else {
            balance -= amount;
            System.out.println("Amount Withdrawn Successfully!");
        }
    }

    public void display() {
        System.out.println("Account No: " + accNo);
        System.out.println("Name: " + name);
        System.out.println("Balance: ₹" + balance);
        System.out.println("---------------------------");
    }
}

public class BankManagementSystem {

    static ArrayList<Account> accounts = new ArrayList<>();
    static final String FILE_NAME = "bank.dat";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        loadData();

        int choice;

        do {
            System.out.println("\n===== BANK MENU =====");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. Display All Accounts");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            try {
                switch (choice) {

                    case 1:
                        System.out.print("Enter Account No: ");
                        int accNo = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Initial Balance: ");
                        double bal = sc.nextDouble();

                        accounts.add(new Account(accNo, name, bal));
                        saveData();
                        break;

                    case 2:
                        Account acc1 = findAccount(sc);
                        if (acc1 != null) {
                            System.out.print("Enter amount: ");
                            double amt = sc.nextDouble();
                            acc1.deposit(amt);
                            saveData();
                        }
                        break;

                    case 3:
                        Account acc2 = findAccount(sc);
                        if (acc2 != null) {
                            System.out.print("Enter amount: ");
                            double amt = sc.nextDouble();
                            acc2.withdraw(amt);
                            saveData();
                        }
                        break;

                    case 4:
                        Account acc3 = findAccount(sc);
                        if (acc3 != null) {
                            acc3.display();
                        }
                        break;

                    case 5:
                        for (Account a : accounts) {
                            a.display();
                        }
                        break;

                    case 6:
                        System.out.println("Thank you!");
                        break;

                    default:
                        System.out.println("Invalid Choice!");
                }
            } catch (Exception e) {
                System.out.println("Invalid Input! Try Again.");
                sc.nextLine();
            }

        } while (choice != 6);

        sc.close();
    }

    static Account findAccount(Scanner sc) {
        System.out.print("Enter Account No: ");
        int accNo = sc.nextInt();

        for (Account a : accounts) {
            if (a.accNo == accNo) {
                return a;
            }
        }
        System.out.println("Account Not Found!");
        return null;
    }

    static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(accounts);
        } catch (IOException e) {
            System.out.println("Error Saving Data!");
        }
    }

    static void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            accounts = (ArrayList<Account>) ois.readObject();
        } catch (Exception e) {
            // File may not exist initially
        }
    }
}
