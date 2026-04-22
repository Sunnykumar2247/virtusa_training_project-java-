public class Bill {
    private String billId, custId, custName, date, paidDate;
    private double units, amount;
    private boolean paid;

    public Bill(String billId, String custId, String custName, double units, double amount, String date) {
        this.billId = billId;
        this.custId = custId;
        this.custName = custName;
        this.units = units;
        this.amount = amount;
        this.date = date;
        this.paid = false;
        this.paidDate = "N/A";
    }

    public String getBillId()    { return billId; }
    public String getCustId()    { return custId; }
    public double getUnits()     { return units; }
    public double getAmount()    { return amount; }
    public boolean isPaid()      { return paid; }

    public void setPaid(boolean v)      { paid = v; }
    public void setPaidDate(String v)   { paidDate = v; }

    public String toCSV() {
        return billId + "," + custId + "," + custName + "," + units + "," + amount + "," + date + "," + paid + "," + paidDate;
    }

    public static Bill fromCSV(String line) {
        String[] p = line.split(",", 8);
        Bill b = new Bill(p[0], p[1], p[2], Double.parseDouble(p[3]), Double.parseDouble(p[4]), p[5]);
        b.setPaid(Boolean.parseBoolean(p[6]));
        b.setPaidDate(p[7]);
        return b;
    }

    public void print() {
        System.out.println("\n  +====================================+");
        System.out.println("  |     ELECTRICITY BILL RECEIPT      |");
        System.out.println("  +====================================+");
        System.out.printf("  | Bill ID   : %-22s |%n", billId);
        System.out.printf("  | Cust ID   : %-22s |%n", custId);
        System.out.printf("  | Name      : %-22s |%n", custName);
        System.out.printf("  | Date      : %-22s |%n", date);
        System.out.println("  +------------------------------------|");
        System.out.printf("  | Units     : %-22.2f |%n", units);
        System.out.printf("  | Amount    : Rs. %-19.2f |%n", amount);
        System.out.println("  +------------------------------------|");
        System.out.printf("  | Status    : %-22s |%n", paid ? "PAID on " + paidDate : "UNPAID");
        System.out.println("  +====================================+");
    }
}
