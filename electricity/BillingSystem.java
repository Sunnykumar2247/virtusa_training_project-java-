import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BillingSystem {

    private List<Customer> customers = new ArrayList<>();
    private List<Bill> bills = new ArrayList<>();
    private int customerCounter = 1;
    private int billCounter = 1;

    // ─── Load & Save ──────────────────────────────────────────────────
    public void loadData() {
        customers = FileHandler.loadCustomers();
        bills = FileHandler.loadBills();
        // Restore counters
        for (Customer c : customers) {
            try {
                int id = Integer.parseInt(c.getCustomerId().replace("CUST", ""));
                if (id >= customerCounter) customerCounter = id + 1;
            } catch (Exception ignored) {}
        }
        for (Bill b : bills) {
            try {
                int id = Integer.parseInt(b.getBillId().replace("BILL", ""));
                if (id >= billCounter) billCounter = id + 1;
            } catch (Exception ignored) {}
        }
        System.out.println("\n Data loaded: " + customers.size() + " customer(s), " + bills.size() + " bill(s).");
    }

    public void saveData() {
        FileHandler.saveCustomers(customers);
        FileHandler.saveBills(bills);
    }

    // ─── Add Customer ─────────────────────────────────────────────────
    public void addCustomer(Scanner sc) {
        System.out.println("\n--- ADD NEW CUSTOMER ---");
        System.out.print(" Name    : "); String name = sc.nextLine().trim();
        System.out.print(" Address : "); String address = sc.nextLine().trim();
        System.out.print(" Phone   : "); String phone = sc.nextLine().trim();

        String type = "";
        while (!type.equals("domestic") && !type.equals("commercial")) {
            System.out.print(" Connection Type (domestic/commercial): ");
            type = sc.nextLine().trim().toLowerCase();
        }

        String id = "CUST" + String.format("%03d", customerCounter++);
        Customer c = new Customer(id, name, address, phone, type);
        customers.add(c);
        saveData();
        System.out.println("\n Customer added successfully! ID: " + id);
    }

    // ─── View All Customers ───────────────────────────────────────────
    public void viewAllCustomers() {
        if (customers.isEmpty()) {
            System.out.println("\n No customers found.");
            return;
        }
        System.out.println("\n--- ALL CUSTOMERS (" + customers.size() + ") ---");
        System.out.printf("%-10s %-20s %-15s %-12s %-10s %-8s%n",
            "ID", "Name", "Phone", "Type", "Curr Read", "Paid?");
        System.out.println("─".repeat(80));
        for (Customer c : customers) {
            System.out.printf("%-10s %-20s %-15s %-12s %-10.1f %-8s%n",
                c.getCustomerId(), c.getName(), c.getPhone(),
                c.getConnectionType(), c.getCurrentReading(),
                c.isBillPaid() ? "YES" : "NO");
        }
    }

    // ─── Generate Bill ────────────────────────────────────────────────
    public void generateBill(Scanner sc) {
        System.out.println("\n--- GENERATE BILL ---");
        BillCalculator.printRateCard();

        System.out.print("\n Enter Customer ID: ");
        String id = sc.nextLine().trim();
        Customer customer = findCustomerById(id);

        if (customer == null) {
            System.out.println(" Customer not found!");
            return;
        }
        if (!customer.isBillPaid()) {
            System.out.println(" This customer has an unpaid bill. Please clear it first.");
            return;
        }

        System.out.printf(" Previous Reading: %.2f%n", customer.getCurrentReading());
        System.out.print(" Enter Current Meter Reading: ");

        double currentReading;
        try {
            currentReading = Double.parseDouble(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println(" Invalid reading entered.");
            return;
        }

        if (currentReading < customer.getCurrentReading()) {
            System.out.println(" Error: Current reading cannot be less than previous reading!");
            return;
        }

        double units = currentReading - customer.getCurrentReading();
        double amount = BillCalculator.calculate(units, customer.getConnectionType());

        customer.setPreviousReading(customer.getCurrentReading());
        customer.setCurrentReading(currentReading);
        customer.setBillPaid(false);

        String billId = "BILL" + String.format("%04d", billCounter++);
        String billDate = LocalDate.now().toString();
        Bill bill = new Bill(billId, customer.getCustomerId(), customer.getName(), units, amount, billDate);
        bills.add(bill);

        saveData();
        bill.printBill();
    }

    // ─── Pay Bill ─────────────────────────────────────────────────────
    public void payBill(Scanner sc) {
        System.out.println("\n--- PAY BILL ---");
        System.out.print(" Enter Customer ID: ");
        String id = sc.nextLine().trim();
        Customer customer = findCustomerById(id);

        if (customer == null) {
            System.out.println(" Customer not found!");
            return;
        }
        if (customer.isBillPaid()) {
            System.out.println(" No pending bill for this customer.");
            return;
        }

        // Find latest unpaid bill for this customer
        Bill latestBill = null;
        for (int i = bills.size() - 1; i >= 0; i--) {
            Bill b = bills.get(i);
            if (b.getCustomerId().equals(id) && !b.isPaid()) {
                latestBill = b;
                break;
            }
        }

        if (latestBill == null) {
            System.out.println(" No unpaid bill found.");
            return;
        }

        latestBill.printBill();
        System.out.printf("%n Amount Due: Rs. %.2f%n", latestBill.getTotalAmount());
        System.out.print(" Confirm payment? (yes/no): ");
        String confirm = sc.nextLine().trim();

        if (confirm.equalsIgnoreCase("yes")) {
            latestBill.setPaid(true);
            latestBill.setPaidDate(LocalDate.now().toString());
            customer.setBillPaid(true);
            saveData();
            System.out.println("\n Payment successful! Bill ID: " + latestBill.getBillId());
        } else {
            System.out.println(" Payment cancelled.");
        }
    }

    // ─── View Bill History ────────────────────────────────────────────
    public void viewBillHistory(Scanner sc) {
        System.out.println("\n--- BILL HISTORY ---");
        System.out.print(" Enter Customer ID (or ALL to view all): ");
        String id = sc.nextLine().trim();

        boolean found = false;
        for (Bill b : bills) {
            if (id.equalsIgnoreCase("ALL") || b.getCustomerId().equalsIgnoreCase(id)) {
                b.printBill();
                found = true;
            }
        }
        if (!found) System.out.println(" No bills found.");
    }

    // ─── Search Customer ──────────────────────────────────────────────
    public void searchCustomer(Scanner sc) {
        System.out.println("\n--- SEARCH CUSTOMER ---");
        System.out.print(" Enter Customer ID or Name: ");
        String query = sc.nextLine().trim().toLowerCase();

        boolean found = false;
        for (Customer c : customers) {
            if (c.getCustomerId().toLowerCase().contains(query) ||
                c.getName().toLowerCase().contains(query)) {
                System.out.println(c);
                found = true;
            }
        }
        if (!found) System.out.println(" No customer found matching: " + query);
    }

    // ─── Delete Customer ──────────────────────────────────────────────
    public void deleteCustomer(Scanner sc) {
        System.out.println("\n--- DELETE CUSTOMER ---");
        System.out.print(" Enter Customer ID to delete: ");
        String id = sc.nextLine().trim();
        Customer customer = findCustomerById(id);

        if (customer == null) {
            System.out.println(" Customer not found!");
            return;
        }
        if (!customer.isBillPaid()) {
            System.out.println(" Cannot delete: Customer has an unpaid bill.");
            return;
        }

        System.out.println(customer);
        System.out.print("\n Are you sure? (yes/no): ");
        if (sc.nextLine().trim().equalsIgnoreCase("yes")) {
            customers.remove(customer);
            saveData();
            System.out.println(" Customer deleted successfully.");
        } else {
            System.out.println(" Deletion cancelled.");
        }
    }

    // ─── Helper ───────────────────────────────────────────────────────
    private Customer findCustomerById(String id) {
        for (Customer c : customers) {
            if (c.getCustomerId().equalsIgnoreCase(id)) return c;
        }
        return null;
    }
}
