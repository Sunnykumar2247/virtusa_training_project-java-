public class Customer {
    private String customerId;
    private String name;
    private String address;
    private String phone;
    private String connectionType; // "domestic" or "commercial"
    private double previousReading;
    private double currentReading;
    private boolean billPaid;

    public Customer(String customerId, String name, String address, String phone, String connectionType) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.connectionType = connectionType;
        this.previousReading = 0;
        this.currentReading = 0;
        this.billPaid = true;
    }

    // Getters and Setters
    public String getCustomerId()         { return customerId; }
    public String getName()               { return name; }
    public String getAddress()            { return address; }
    public String getPhone()              { return phone; }
    public String getConnectionType()     { return connectionType; }
    public double getPreviousReading()    { return previousReading; }
    public double getCurrentReading()     { return currentReading; }
    public boolean isBillPaid()           { return billPaid; }

    public void setName(String name)                      { this.name = name; }
    public void setAddress(String address)                { this.address = address; }
    public void setPhone(String phone)                    { this.phone = phone; }
    public void setPreviousReading(double prev)           { this.previousReading = prev; }
    public void setCurrentReading(double curr)            { this.currentReading = curr; }
    public void setBillPaid(boolean paid)                 { this.billPaid = paid; }

    // Convert to CSV string for file storage
    public String toCSV() {
        return customerId + "," + name + "," + address + "," + phone + "," +
               connectionType + "," + previousReading + "," + currentReading + "," + billPaid;
    }

    // Create Customer from CSV string
    public static Customer fromCSV(String csv) {
        String[] parts = csv.split(",", 8);
        Customer c = new Customer(parts[0], parts[1], parts[2], parts[3], parts[4]);
        c.setPreviousReading(Double.parseDouble(parts[5]));
        c.setCurrentReading(Double.parseDouble(parts[6]));
        c.setBillPaid(Boolean.parseBoolean(parts[7]));
        return c;
    }

    @Override
    public String toString() {
        return String.format(
            "+------------------------------------------+\n" +
            "| Customer ID   : %-24s |\n" +
            "| Name          : %-24s |\n" +
            "| Address       : %-24s |\n" +
            "| Phone         : %-24s |\n" +
            "| Connection    : %-24s |\n" +
            "| Prev Reading  : %-24.2f |\n" +
            "| Curr Reading  : %-24.2f |\n" +
            "| Bill Status   : %-24s |\n" +
            "+------------------------------------------+",
            customerId, name, address, phone, connectionType,
            previousReading, currentReading,
            billPaid ? "PAID" : "UNPAID"
        );
    }
}
