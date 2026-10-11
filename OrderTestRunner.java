
import java.util.Locale;
public class OrderTestRunner {
    public static void main(String[] args) {
        // Membuat minimal 4 objek OrderTestCase dalam bentuk array
        OrderTestCase[] orderSuite = new OrderTestCase[4];

        orderSuite[0] = new OrderTestCase("ORD-2026-001", "Andi", 50000.0, 2, 10.0);    // Grand Total: 90.000
        orderSuite[1] = new OrderTestCase("ORD-2026-002", "Budi", 150000.0, 1, 20.0);   // Grand Total: 120.000
        orderSuite[2] = new OrderTestCase("ORD-2026-003", "Citra", 25000.0, 4, 0.0);    // Grand Total: 100.000
        orderSuite[3] = new OrderTestCase("ORD-2026-004", "Dewi", 75000.0, 3, 15.0);    // Grand Total: 191.250

        // Simulasi nominal pembayaran aktual oleh pelanggan (sengaja dibuat ada yang pas dan ada yang selisih untuk pengujian)
       double[] simulatedPayments = {
           90000.0,   // Sesuai (PASS)
           115000.0,  // Selisih (FAIL)
           100000.0,  // Sesuai (PASS)
            190000.0   // Selisih (FAIL)
        };





//         // Header Tabel Konsol
          System.out.println("=".repeat(97));
        System.out.printf("| %-14s| %-14s| %-13s| %-8s| %-9s| %-14s| %-10s|\n",
                "Order ID", "Customer Name", "Item Price", "Qty", "Disc (%)", "Grand Total", "Status");
      System.out.println("=".repeat(97));

        // Evaluasi dan Cetak Baris Test Case
               for (int i = 0; i < orderSuite.length; i++) {
           orderSuite[i].evaluatePayment(simulatedPayments[i]);
           orderSuite[i].displayRow();
                  }

          System.out.println("=".repeat(97));
    }
 }

