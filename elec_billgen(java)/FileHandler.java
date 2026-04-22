import java.io.*;
import java.util.*;

public class FileHandler {

    private static final String C_FILE = "customers.txt";
    private static final String B_FILE = "bills.txt";

    public static void saveCustomers(List<Customer> list) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(C_FILE))) {
            for (Customer c : list) { w.write(c.toCSV()); w.newLine(); }
        } catch (IOException e) { System.out.println("Save error: " + e.getMessage()); }
    }

    public static List<Customer> loadCustomers() {
        List<Customer> list = new ArrayList<>();
        File f = new File(C_FILE);
        if (!f.exists()) return list;
        try (BufferedReader r = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = r.readLine()) != null)
                if (!line.trim().isEmpty()) list.add(Customer.fromCSV(line));
        } catch (IOException e) { System.out.println("Load error: " + e.getMessage()); }
        return list;
    }

    public static void saveBills(List<Bill> list) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(B_FILE))) {
            for (Bill b : list) { w.write(b.toCSV()); w.newLine(); }
        } catch (IOException e) { System.out.println("Save error: " + e.getMessage()); }
    }

    public static List<Bill> loadBills() {
        List<Bill> list = new ArrayList<>();
        File f = new File(B_FILE);
        if (!f.exists()) return list;
        try (BufferedReader r = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = r.readLine()) != null)
                if (!line.trim().isEmpty()) list.add(Bill.fromCSV(line));
        } catch (IOException e) { System.out.println("Load error: " + e.getMessage()); }
        return list;
    }
}
