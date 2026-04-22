public class BillCalculator {

    public static double calculate(double units, String type) {
        double charge, fixed, tax;

        if (type.equalsIgnoreCase("domestic")) {
            fixed = 50;
            tax = 0.05;
            if (units <= 100)       charge = units * 3.50;
            else if (units <= 200)  charge = 350 + (units - 100) * 5.00;
            else if (units <= 300)  charge = 850 + (units - 200) * 6.50;
            else                    charge = 1500 + (units - 300) * 8.00;
        } else {
            fixed = 150;
            tax = 0.10;
            if (units <= 100)       charge = units * 6.00;
            else if (units <= 300)  charge = 600 + (units - 100) * 8.50;
            else                    charge = 2300 + (units - 300) * 10.00;
        }

        double subtotal = charge + fixed;
        return subtotal + (subtotal * tax);
    }

    public static void showRates() {
        System.out.println("\n  DOMESTIC : 0-100=3.50 | 101-200=5.00 | 201-300=6.50 | 300+=8.00 | Fixed=50 | Tax=5%");
        System.out.println("  COMMERCIAL: 0-100=6.00 | 101-300=8.50 | 300+=10.00  | Fixed=150 | Tax=10%");
    }
}
