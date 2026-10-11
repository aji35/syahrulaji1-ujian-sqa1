public class OrderTestCase {
    // 1. Attributes / Fields
    private String orderId;
    private String customerName;
    private double itemPrice;
    private int quantity;
    private double voucherDiscountPercent;
    private double actualAmountPaid;
    private String testStatus;
    private String failureReason;

    // 2. Constructor Berparameter
    public OrderTestCase(String orderId, String customerName, double itemPrice, int quantity,
            double voucherDiscountPercent) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        this.voucherDiscountPercent = voucherDiscountPercent;
        this.testStatus = "BLOCKED"; // Default awal sebelum dievaluasi
        this.failureReason = "-";
    }

    // 3. Methods
    public double calculateSubtotal() {
        return itemPrice * quantity;
    }

    public double calculateDiscountAmount() {
        return calculateSubtotal() * (voucherDiscountPercent / 100.0);
    }

    public double calculateGrandTotal() {
        return calculateSubtotal() - calculateDiscountAmount();
    }

    public void evaluatePayment(double actualAmountPaid) {
        this.actualAmountPaid = actualAmountPaid;
        double expectedGrandTotal = calculateGrandTotal();
        double tolerance = 0.001;

        if (Math.abs(actualAmountPaid - expectedGrandTotal) <= tolerance) {
            this.testStatus = "PASS";
            this.failureReason = "-";
        } else {
            this.testStatus = "FAIL";
            double diff = actualAmountPaid - expectedGrandTotal;
            this.failureReason = String.format("Selisih: %.2f", diff);
        }
    }

    public boolean isFailed() {
        return testStatus.equals("FAIL");
    }

    // ===== TABEL 1: Ringkasan semua test case =====
    private static String line(int... widths) {
        StringBuilder sb = new StringBuilder("+");
        for (int w : widths) {
            sb.append("-".repeat(w + 2)).append("+");
        }
        return sb.toString();
    }

    public static void printMainHeader() {
        String sep = line(10, 15, 12, 5, 8, 14, 8);
        System.out.println(sep);
        System.out.printf("| %-10s | %-15s | %12s | %5s | %8s | %14s | %-8s |\n",
                "Order ID", "Customer", "Harga", "Qty", "Diskon", "Grand Total", "Status");
        System.out.println(sep);
    }

    public void displayRow() {
        System.out.printf("| %-10s | %-15s | %12.2f | %5d | %7.1f%% | %14.2f | %-8s |\n",
                orderId, customerName, itemPrice, quantity, voucherDiscountPercent,
                calculateGrandTotal(), testStatus);
    }

    public static void printMainFooter() {
        System.out.println(line(10, 15, 12, 5, 8, 14, 8));
    }

    // ===== TABEL 2: Rincian test case yang FAIL =====
    public static void printFailHeader() {
        String sep = line(10, 15, 14, 14, 14);
        System.out.println(sep);
        System.out.printf("| %-10s | %-15s | %14s | %14s | %14s |\n",
                "Order ID", "Customer", "Seharusnya", "Dibayar", "Selisih");
        System.out.println(sep);
    }

    public void displayFailRow() {
        double expected = calculateGrandTotal();
        System.out.printf("| %-10s | %-15s | %14.2f | %14.2f | %+14.2f |\n",
                orderId, customerName, expected, actualAmountPaid, actualAmountPaid - expected);
    }

    public static void printFailFooter() {
        System.out.println(line(10, 15, 14, 14, 14));
    }

    // ===== Contoh pemakaian =====
    public static void main(String[] args) {
        OrderTestCase[] tests = {
                new OrderTestCase("ORD-001", "Budi", 150000, 2, 10),
                new OrderTestCase("ORD-002", "Siti Aminah", 75000.50, 3, 0),
                new OrderTestCase("ORD-003", "Andi", 200000, 1, 25)
        };

        tests[0].evaluatePayment(270000.00); // PASS
        tests[1].evaluatePayment(225000.00); // FAIL
        tests[2].evaluatePayment(150000.00); // PASS

        System.out.println("TABEL 1: HASIL PENGUJIAN");
        printMainHeader();
        for (OrderTestCase t : tests) {
            t.displayRow();
        }
        printMainFooter();

        System.out.println("\nTABEL 2: RINCIAN TEST CASE GAGAL");
        printFailHeader();
        for (OrderTestCase t : tests) {
            if (t.isFailed()) {
                t.displayFailRow();
            }
        }
        printFailFooter();
    }
}