import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private static final String CUSTOMERS_FILE = "customers.txt";
    private static final String BILLS_FILE = "bills.txt";

    // ─── Save Customers ───────────────────────────────────────────────
    public static void saveCustomers(List<Customer> customers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOMERS_FILE))) {
            for (Customer c : customers) {
                writer.write(c.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println(" Error saving customers: " + e.getMessage());
        }
    }

    // ─── Load Customers ───────────────────────────────────────────────
    public static List<Customer> loadCustomers() {
        List<Customer> customers = new ArrayList<>();
        File file = new File(CUSTOMERS_FILE);
        if (!file.exists()) return customers;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    customers.add(Customer.fromCSV(line));
                }
            }
        } catch (IOException e) {
            System.out.println(" Error loading customers: " + e.getMessage());
        }
        return customers;
    }

    // ─── Save Bills ───────────────────────────────────────────────────
    public static void saveBills(List<Bill> bills) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BILLS_FILE))) {
            for (Bill b : bills) {
                writer.write(b.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println(" Error saving bills: " + e.getMessage());
        }
    }

    // ─── Load Bills ───────────────────────────────────────────────────
    public static List<Bill> loadBills() {
        List<Bill> bills = new ArrayList<>();
        File file = new File(BILLS_FILE);
        if (!file.exists()) return bills;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    bills.add(Bill.fromCSV(line));
                }
            }
        } catch (IOException e) {
            System.out.println(" Error loading bills: " + e.getMessage());
        }
        return bills;
    }
}
