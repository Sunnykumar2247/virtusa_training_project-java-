public class Customer {
    private String id, name, address, phone, type;
    private double prevRead, currRead;
    private boolean paid;

    public Customer(String id, String name, String address, String phone, String type) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.type = type;
        this.prevRead = 0;
        this.currRead = 0;
        this.paid = true;
    }

    public String getId()        { return id; }
    public String getName()      { return name; }
    public String getAddress()   { return address; }
    public String getPhone()     { return phone; }
    public String getType()      { return type; }
    public double getPrevRead()  { return prevRead; }
    public double getCurrRead()  { return currRead; }
    public boolean isPaid()      { return paid; }

    public void setPrevRead(double v) { prevRead = v; }
    public void setCurrRead(double v) { currRead = v; }
    public void setPaid(boolean v)    { paid = v; }

    public String toCSV() {
        return id + "," + name + "," + address + "," + phone + "," + type + "," + prevRead + "," + currRead + "," + paid;
    }

    public static Customer fromCSV(String line) {
        String[] p = line.split(",", 8);
        Customer c = new Customer(p[0], p[1], p[2], p[3], p[4]);
        c.setPrevRead(Double.parseDouble(p[5]));
        c.setCurrRead(Double.parseDouble(p[6]));
        c.setPaid(Boolean.parseBoolean(p[7]));
        return c;
    }

    @Override
    public String toString() {
        return String.format(
            "+-----------------------------------------+\n" +
            "| ID       : %-29s |\n" +
            "| Name     : %-29s |\n" +
            "| Address  : %-29s |\n" +
            "| Phone    : %-29s |\n" +
            "| Type     : %-29s |\n" +
            "| Readings : %.1f  ->  %-20.1f |\n" +
            "| Status   : %-29s |\n" +
            "+-----------------------------------------+",
            id, name, address, phone, type, prevRead, currRead, paid ? "PAID" : "UNPAID"
        );
    }
}
