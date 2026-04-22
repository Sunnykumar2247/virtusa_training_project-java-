import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BillingSystem sys = new BillingSystem();
        sys.load();

        int ch;
        do {
            System.out.println("\n==============================");
            System.out.println("   ELECTRICITY BILLING SYSTEM");
            System.out.println("==============================");
            System.out.println(" 1. Add Customer");
            System.out.println(" 2. View Customers");
            System.out.println(" 3. Generate Bill");
            System.out.println(" 4. Pay Bill");
            System.out.println(" 5. Bill History");
            System.out.println(" 6. Search Customer");
            System.out.println(" 7. Delete Customer");
            System.out.println(" 0. Exit");
            System.out.print(" Choice: ");

            ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1: sys.addCustomer(sc); break;
                case 2: sys.viewCustomers(); break;
                case 3: sys.generateBill(sc); break;
                case 4: sys.payBill(sc); break;
                case 5: sys.billHistory(sc); break;
                case 6: sys.searchCustomer(sc); break;
                case 7: sys.deleteCustomer(sc); break;
                case 0: sys.save(); System.out.println("Goodbye!"); break;
                default: System.out.println("Invalid choice.");
            }
        } while (ch != 0);

        sc.close();
    }
}
