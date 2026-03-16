import java.time.LocalDate;

public class Bill {
    private String billId;
    private String customerId;
    private String customerName;
    private double unitsConsumed;
    private double totalAmount;
    private String billDate;
    private boolean paid;
    private String paidDate;

    public Bill(String billId, String customerId, String customerName,
                double unitsConsumed, double totalAmount, String billDate) {
        this.billId = billId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.unitsConsumed = unitsConsumed;
        this.totalAmount = totalAmount;
        this.billDate = billDate;
        this.paid = false;
        this.paidDate = "N/A";
    }

    // Getters
    public String getBillId()         { return billId; }
    public String getCustomerId()     { return customerId; }
    public String getCustomerName()   { return customerName; }
    public double getUnitsConsumed()  { return unitsConsumed; }
    public double getTotalAmount()    { return totalAmount; }
    public String getBillDate()       { return billDate; }
    public boolean isPaid()           { return paid; }
    public String getPaidDate()       { return paidDate; }

    public void setPaid(boolean paid)         { this.paid = paid; }
    public void setPaidDate(String paidDate)  { this.paidDate = paidDate; }

    // CSV for file storage
    public String toCSV() {
        return billId + "," + customerId + "," + customerName + "," +
               unitsConsumed + "," + totalAmount + "," + billDate + "," + paid + "," + paidDate;
    }

    public static Bill fromCSV(String csv) {
        String[] parts = csv.split(",", 8);
        Bill b = new Bill(parts[0], parts[1], parts[2],
                Double.parseDouble(parts[3]), Double.parseDouble(parts[4]), parts[5]);
        b.setPaid(Boolean.parseBoolean(parts[6]));
        b.setPaidDate(parts[7]);
        return b;
    }

    public void printBill() {
        System.out.println("\n  ╔══════════════════════════════════════╗");
        System.out.println("  ║       ELECTRICITY BILL RECEIPT       ║");
        System.out.println("  ╠══════════════════════════════════════╣");
        System.out.printf("  ║ Bill ID     : %-22s ║%n", billId);
        System.out.printf("  ║ Customer ID : %-22s ║%n", customerId);
        System.out.printf("  ║ Name        : %-22s ║%n", customerName);
        System.out.printf("  ║ Bill Date   : %-22s ║%n", billDate);
        System.out.println("  ╠══════════════════════════════════════╣");
        System.out.printf("  ║ Units Used  : %-22.2f ║%n", unitsConsumed);
        System.out.printf("  ║ Total Amount: Rs. %-19.2f ║%n", totalAmount);
        System.out.println("  ╠══════════════════════════════════════╣");
        System.out.printf("  ║ Status      : %-22s ║%n", paid ? "✅ PAID on " + paidDate : "❌ UNPAID");
        System.out.println("  ╚══════════════════════════════════════╝");
    }
}
