public class BillCalculator {

    /*
     * DOMESTIC RATE SLAB:
     *   0  - 100  units: Rs. 3.50 per unit
     *   101 - 200 units: Rs. 5.00 per unit
     *   201 - 300 units: Rs. 6.50 per unit
     *   300+      units: Rs. 8.00 per unit
     *   Fixed Charge: Rs. 50
     *   Tax: 5%
     *
     * COMMERCIAL RATE SLAB:
     *   0  - 100  units: Rs. 6.00 per unit
     *   101 - 300 units: Rs. 8.50 per unit
     *   300+      units: Rs. 10.00 per unit
     *   Fixed Charge: Rs. 150
     *   Tax: 10%
     */

    public static double calculate(double units, String connectionType) {
        double charge = 0;
        double fixedCharge;
        double taxRate;

        if (connectionType.equalsIgnoreCase("domestic")) {
            fixedCharge = 50;
            taxRate = 0.05;

            if (units <= 100) {
                charge = units * 3.50;
            } else if (units <= 200) {
                charge = (100 * 3.50) + ((units - 100) * 5.00);
            } else if (units <= 300) {
                charge = (100 * 3.50) + (100 * 5.00) + ((units - 200) * 6.50);
            } else {
                charge = (100 * 3.50) + (100 * 5.00) + (100 * 6.50) + ((units - 300) * 8.00);
            }

        } else { // commercial
            fixedCharge = 150;
            taxRate = 0.10;

            if (units <= 100) {
                charge = units * 6.00;
            } else if (units <= 300) {
                charge = (100 * 6.00) + ((units - 100) * 8.50);
            } else {
                charge = (100 * 6.00) + (200 * 8.50) + ((units - 300) * 10.00);
            }
        }

        double subTotal = charge + fixedCharge;
        double tax = subTotal * taxRate;
        return subTotal + tax;
    }

    public static void printRateCard() {
        System.out.println("\n  ┌─────────────────────────────────────────┐");
        System.out.println("  │             RATE CARD                   │");
        System.out.println("  ├─────────────────────────────────────────┤");
        System.out.println("  │  DOMESTIC CONNECTION                    │");
        System.out.println("  │  0-100 units    : Rs. 3.50/unit         │");
        System.out.println("  │  101-200 units  : Rs. 5.00/unit         │");
        System.out.println("  │  201-300 units  : Rs. 6.50/unit         │");
        System.out.println("  │  300+ units     : Rs. 8.00/unit         │");
        System.out.println("  │  Fixed Charge   : Rs. 50   Tax: 5%      │");
        System.out.println("  ├─────────────────────────────────────────┤");
        System.out.println("  │  COMMERCIAL CONNECTION                  │");
        System.out.println("  │  0-100 units    : Rs. 6.00/unit         │");
        System.out.println("  │  101-300 units  : Rs. 8.50/unit         │");
        System.out.println("  │  300+ units     : Rs. 10.00/unit        │");
        System.out.println("  │  Fixed Charge   : Rs. 150  Tax: 10%     │");
        System.out.println("  └─────────────────────────────────────────┘");
    }
}
