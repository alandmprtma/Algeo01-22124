import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        // Scanner definition
        Scanner scanner = new Scanner(System.in);
        
        // Header
        slowprint("MENU");

        String[] menu = {
            "1. Sistem Persamaan Linear", 
            "2. Determinan", 
            "3. Matriks Balikan", 
            "4. Interpolasi Polinom", 
            "5. Interpolasi Bicubic Spline", 
            "6. Regresi Linier Berganda", 
            "7. Keluar"};
        for (String x : menu) {
            slowprint(x);
        }

        // Ask for menu input
        int choice = askInput(1, 7, scanner);

        // Run code for chosen menu item
        if (choice == 1) {
            SPL.driverSPL(scanner);
        }

        else if (choice == 2) {
            Determinan.DriverDeterminan(scanner);
        }

        else if (choice == 3) {
            Balikan.driverBalikan(scanner);
        }

        else if (choice == 4) {
            PolynomInterpolation.PolynomInterpolationDriver(scanner);
        }

        else if (choice == 5) {
            BicubicSplineInterpolation.BicubicSplineInterpolationDriver(scanner);
        }

        // Close scanner
        scanner.close();
    }

    // Additional Functions
    // 1. Ask for terminal matrix input
    public static Matriks askMatriksInput(Scanner scanner, int type) {
        Matriks matriks = new Matriks(0, 0);
        
        // Header
        slowprint("\nPilih metode untuk input: ");
        
        String[] input = {
            "1. Input melalui terminal", 
            "2. Input dari suatu file .txt"
        };
        printMenu(input);

        int choice = askInput(1, 2, scanner);

        // Input terminal
        if (choice == 1) {
            matriks.readMatrix(scanner, type);
            return matriks;
        
        // Input file
        } else {
            Matriks m = askForMatrixFromFile(scanner);
            return m;
        }
    }

    // 2. Slowly prints to terminal
    public static void slowprint(String str) {
        for (char c : str.toCharArray()) {
            System.out.print(c);

            try {
                Thread.sleep(5); // Default: 50
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }

        try {
            Thread.sleep(100); // Default: 400
        } catch (InterruptedException e) {
                System.out.println(e);
        }

        System.out.println();
    }

    // 3. slowprint without newline
    public static void slowprintNoNewline(String str) {
       for (char c : str.toCharArray()) {
            System.out.print(c);

            try {
                Thread.sleep(5); // Default: 50
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }

        try {
            Thread.sleep(100); // Default: 400
        } catch (InterruptedException e) {
                System.out.println(e);
        }
    }

    // 4. Ask for integer input
    public static int askInput(int low, int high, Scanner scanner) {
        int choice = low;
        // Loop while input invalid
        boolean inputInteger = true;

        do {
            System.out.print("Input menu: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                inputInteger = true;

                if (choice < low || choice > high) {
                    slowprint("\nInput tidak valid. Silahkan masukkan kembali input integer.");
                }
            } else {
                scanner.next();
                slowprint("\nInput tidak valid. Silahkan masukkan kembali input integer.");
                inputInteger = false;
            }
        } while (choice < low || choice > high || !inputInteger);
    
        return choice;
    }

    // 5. Print menu
    public static void printMenu(String[] menu) {
        for (String x : menu) {
            slowprint(x);
        }
    }

    // 6. Ask matrix input from file
    public static Matriks askForMatrixFromFile(Scanner scanner) {
        String filename;
        File file;
        boolean fileValid = true;

        do {
            System.out.print("Masukkan nama file matriks: ");
            filename = scanner.next();
            file = new File("../test/" + filename + ".txt");
            
            if (!file.exists()) {
                slowprint("File tidak ditemukan! Mohon masukkan kembali nama file: ");
                fileValid = false;
            } else {
                fileValid = true;
            }
        } while (!fileValid);

        return Matriks.ReadMatrixFromFile(file);
    }
}
