import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BillingSystem system = new BillingSystem();
        system.loadData(); // Load from file on startup

        int choice;
        do {
            System.out.println("\n========================================");
            System.out.println("   ⚡ ELECTRICITY BILLING SYSTEM ⚡");
            System.out.println("========================================");
            System.out.println(" 1. Add New Customer");
            System.out.println(" 2. View All Customers");
            System.out.println(" 3. Generate Bill");
            System.out.println(" 4. Pay Bill");
            System.out.println(" 5. View Bill History");
            System.out.println(" 6. Search Customer");
            System.out.println(" 7. Delete Customer");
            System.out.println(" 0. Exit");
            System.out.println("========================================");
            System.out.print(" Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1: system.addCustomer(scanner); break;
                case 2: system.viewAllCustomers(); break;
                case 3: system.generateBill(scanner); break;
                case 4: system.payBill(scanner); break;
                case 5: system.viewBillHistory(scanner); break;
                case 6: system.searchCustomer(scanner); break;
                case 7: system.deleteCustomer(scanner); break;
                case 0:
                    system.saveData(); // Save to file on exit
                    System.out.println("\n Data saved. Goodbye!\n");
                    break;
                default:
                    System.out.println("\n Invalid choice! Please try again.");
            }
        } while (choice != 0);

        scanner.close();
    }
}
