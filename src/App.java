import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("MENU");

        String[] menu = {"1. Sistem Persamaan Linear", "2. Determinan", "3. Matriks Balikan"};
        for (String x : menu) {
            System.out.println(x);
        }

        System.out.print("\nPilih menu: ");
        int choice = scanner.nextInt();

        // Close scanner
        scanner.close();
    }
}
