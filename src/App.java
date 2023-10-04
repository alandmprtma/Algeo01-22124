import java.io.File;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("MENU");


        String[] menu = {"1. Sistem Persamaan Linear", "2. Determinan", "3. Matriks Balikan", "4. Interpolasi Polinom", "5. Interpolasi Bicubic Spline", "6. Regresi Linier Berganda", "7. Keluar"};
        for (String x : menu) {
            System.out.println(x);
        }

        System.out.print("\nPilih menu: ");
        int choice = scanner.nextInt();
        while (choice < 1 && choice > 7) {
            System.out.print("\nPilih menu: ");
            choice = scanner.nextInt();
        }
        if (choice == 1)
        {
            System.out.println("Sistem Persamaan Linear");

            String[] SPL_choice = {"1. Metode eliminasi Gauss", "2. Metode eliminasi Gauss-Jordan", "3. Metode matriks balikan", "4. Kaidah cramer"};
            for (String x : SPL_choice)
            {
                System.out.println(x);
            }
            System.out.print("\nPilih menu: ");
            int choiceSPL = scanner.nextInt();
            while (choiceSPL < 1 && choiceSPL > 4) {
                System.out.print("\nPilih menu: ");
                choiceSPL = scanner.nextInt();
            }
            if (choiceSPL == 1)
            {
                Matriks m = askMatriksInput(scanner);
            }
            else if (choiceSPL == 2)
            {
                Matriks m = askMatriksInput(scanner);
            }
            else if (choiceSPL == 3)
            {
                Matriks m = askMatriksInput(scanner);
                SPL.SPLBalikan(m);
            }
            else
            {
                Matriks m = askMatriksInput(scanner);
                SPL.SPLCramer(m);
            }
        }

        else if (choice == 2)
        {
            System.out.println("Determinan");
            String[] Determinan_choice = {"1. Determinan dengan operasi baris elementer (OBE)", "2. Determinan dengan ekspansi kofaktor"};
            for (String x : Determinan_choice)
            {
                System.out.println(x);
            }
            System.out.print("\nPilih menu: ");
            int choiceDeterminan = scanner.nextInt();
            while (choiceDeterminan != 1 && choiceDeterminan != 2)
            {
                System.out.print("\nPilih menu: ");
                choiceDeterminan = scanner.nextInt();
            }
            if (choiceDeterminan == 1) {
                Matriks m = askMatriksInput(scanner);
            }
            else{
                Matriks m = askMatriksInput(scanner);
                Determinan.DeterminanKofaktor(m); 
            }
        }

        else if (choice == 3)
        {
            System.out.println("Matriks Balikan");
            String[] balikan_choice = {"1. Matriks balikan dengan metode Gauss-Jordan", "2. Matriks balikan dengan adjoin"};
            for (String x : balikan_choice)
            {
                System.out.println(x);
            }
            System.out.print("\nPilih menu: ");
            int choiceBalikan = scanner.nextInt();
            while (choiceBalikan != 1 && choiceBalikan != 2)
            {
                System.out.print("\nPilih menu: ");
                choiceBalikan = scanner.nextInt();
            }
            if (choiceBalikan == 1)
            {
                Matriks m = askMatriksInput(scanner);
                Balikan.BalikanGaussJordan(m);
            }
            else{
                Matriks m = askMatriksInput(scanner);
                Balikan.BalikanAdjoin(m);
            }
        }

        


        // Close scanner
        scanner.close();
    }

    public static Matriks askMatriksInput(Scanner scanner) {
        Matriks matriks = new Matriks(0, 0);
        System.out.println("\nPilih metode untuk input: ");
        String[] input = {"1. Input melalui terminal", "2. Input dari suatu file .txt"};
        for (String x : input)
        {
            System.out.println(x);
        }
        System.out.print("Masukkan pilihan: ");
        int choiceinput = scanner.nextInt();
        scanner.nextLine();
        if (choiceinput == 1)
        {
            matriks.readMatrix(scanner);

            return matriks;
        }
        else if (choiceinput == 2)
        {
            System.out.print("Masukkan nama file matriks: ");
            String cdSPL; 
            cdSPL = scanner.nextLine();
            cdSPL = "../test/" + cdSPL + ".txt";
            File file = new File(cdSPL);
            while (!file.exists()) {
                System.out.print("File tidak ditemukan! Mohon masukkan kembali nama file: ");
                cdSPL = scanner.nextLine();

                cdSPL = "../test/" + cdSPL + ".txt";
                file = new File(cdSPL);
                }

            Matriks m = Matriks.ReadMatrixFromFile(file);

            return m;
        }
        else
        {
            System.out.println("\nInput tidak valid!");
            return askMatriksInput(scanner);
        }
    }
}
