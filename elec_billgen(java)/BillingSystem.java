import java.time.LocalDate;
import java.util.*;

public class BillingSystem {

    private List<Customer> customers = new ArrayList<>();
    private List<Bill> bills = new ArrayList<>();
    private int cCount = 1, bCount = 1;

    public void load() {
        customers = FileHandler.loadCustomers();
        bills = FileHandler.loadBills();
        for (Customer c : customers) {
            try {
                int n = Integer.parseInt(c.getId().replace("C", ""));
                if (n >= cCount) cCount = n + 1;
            } catch (Exception ignored) {}
        }
        for (Bill b : bills) {
            try {
                int n = Integer.parseInt(b.getBillId().replace("B", ""));
                if (n >= bCount) bCount = n + 1;
            } catch (Exception ignored) {}
        }
        System.out.println("Loaded: " + customers.size() + " customer(s), " + bills.size() + " bill(s).");
    }

    public void save() {
        FileHandler.saveCustomers(customers);
        FileHandler.saveBills(bills);
    }

    public void addCustomer(Scanner sc) {
        System.out.println("\n-- ADD CUSTOMER --");
        System.out.print("Name    : "); String name = sc.nextLine().trim();
        System.out.print("Address : "); String addr = sc.nextLine().trim();
        System.out.print("Phone   : "); String phone = sc.nextLine().trim();
        String type = "";
        while (!type.equals("domestic") && !type.equals("commercial")) {
            System.out.print("Type (domestic/commercial): ");
            type = sc.nextLine().trim().toLowerCase();
        }
        String id = "C" + String.format("%03d", cCount++);
        customers.add(new Customer(id, name, addr, phone, type));
        save();
        System.out.println("Added! ID: " + id);
    }

    public void viewCustomers() {
        if (customers.isEmpty()) { System.out.println("No customers."); return; }
        System.out.printf("%n%-8s %-18s %-13s %-12s %-8s%n", "ID", "Name", "Phone", "Type", "Paid?");
        System.out.println("-".repeat(65));
        for (Customer c : customers)
            System.out.printf("%-8s %-18s %-13s %-12s %-8s%n",
                c.getId(), c.getName(), c.getPhone(), c.getType(), c.isPaid() ? "YES" : "NO");
    }

    public void generateBill(Scanner sc) {
        BillCalculator.showRates();
        System.out.print("\nCustomer ID: "); String id = sc.nextLine().trim();
        Customer c = find(id);
        if (c == null)       { System.out.println("Not found."); return; }
        if (!c.isPaid())     { System.out.println("Unpaid bill exists. Clear it first."); return; }

        System.out.printf("Previous reading: %.2f%n", c.getCurrRead());
        System.out.print("Current reading : ");
        double curr;
        try { curr = Double.parseDouble(sc.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("Invalid input."); return; }

        if (curr < c.getCurrRead()) { System.out.println("Cannot be less than previous."); return; }

        double units = curr - c.getCurrRead();
        double amount = BillCalculator.calculate(units, c.getType());
        c.setPrevRead(c.getCurrRead());
        c.setCurrRead(curr);
        c.setPaid(false);

        String bid = "B" + String.format("%04d", bCount++);
        Bill b = new Bill(bid, c.getId(), c.getName(), units, amount, LocalDate.now().toString());
        bills.add(b);
        save();
        b.print();
    }

    public void payBill(Scanner sc) {
        System.out.print("\nCustomer ID: "); String id = sc.nextLine().trim();
        Customer c = find(id);
        if (c == null)    { System.out.println("Not found."); return; }
        if (c.isPaid())   { System.out.println("No pending bill."); return; }

        Bill pending = null;
        for (int i = bills.size() - 1; i >= 0; i--)
            if (bills.get(i).getCustId().equals(id) && !bills.get(i).isPaid()) { pending = bills.get(i); break; }

        if (pending == null) { System.out.println("No unpaid bill found."); return; }

        pending.print();
        System.out.printf("Amount Due: Rs. %.2f%n", pending.getAmount());
        System.out.print("Confirm? (yes/no): ");
        if (sc.nextLine().trim().equalsIgnoreCase("yes")) {
            pending.setPaid(true);
            pending.setPaidDate(LocalDate.now().toString());
            c.setPaid(true);
            save();
            System.out.println("Payment done! Bill: " + pending.getBillId());
        } else {
            System.out.println("Cancelled.");
        }
    }

    public void billHistory(Scanner sc) {
        System.out.print("\nCustomer ID (or ALL): "); String id = sc.nextLine().trim();
        boolean found = false;
        for (Bill b : bills)
            if (id.equalsIgnoreCase("ALL") || b.getCustId().equalsIgnoreCase(id)) { b.print(); found = true; }
        if (!found) System.out.println("No bills found.");
    }

    public void searchCustomer(Scanner sc) {
        System.out.print("\nSearch (ID or name): "); String q = sc.nextLine().trim().toLowerCase();
        boolean found = false;
        for (Customer c : customers)
            if (c.getId().toLowerCase().contains(q) || c.getName().toLowerCase().contains(q)) { System.out.println(c); found = true; }
        if (!found) System.out.println("No match found.");
    }

    public void deleteCustomer(Scanner sc) {
        System.out.print("\nCustomer ID to delete: "); String id = sc.nextLine().trim();
        Customer c = find(id);
        if (c == null)    { System.out.println("Not found."); return; }
        if (!c.isPaid())  { System.out.println("Cannot delete: unpaid bill exists."); return; }
        System.out.println(c);
        System.out.print("Confirm? (yes/no): ");
        if (sc.nextLine().trim().equalsIgnoreCase("yes")) { customers.remove(c); save(); System.out.println("Deleted."); }
        else System.out.println("Cancelled.");
    }

    private Customer find(String id) {
        for (Customer c : customers)
            if (c.getId().equalsIgnoreCase(id)) return c;
        return null;
    }
}
