import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Determinan {
    // Methods
    public static void main(String[] args) {
        // Create scanner
        Scanner scanner = new Scanner(System.in);

        Matriks m = new Matriks(0, 0);
        m.readMatrix(scanner);
        m.printMatrix();

        System.out.println();
        double det = DeterminanKofaktor(m);
        System.out.printf("Determinan: %f", det);

        // Close scanner
        scanner.close();
    }

    // 1. PrintDeterminanToFile
    public static void PrintDeterminantoFile(double determinan) {
        try (Scanner scanner = new Scanner(System.in)){
            System.out.print("Masukkan nama file untuk menyimpan hasil interpolasi : ");
            String cdfile;
            cdfile = scanner.nextLine();
            cdfile = "../output/" + cdfile + ".txt";
            BufferedWriter tulis = new BufferedWriter(new FileWriter(cdfile));
            for (int i = 0; i < 1; i++)
            {
                tulis.write("Nilai determinan matriks adalah "+determinan+".");
            }
            tulis.close();
            System.out.println("Data telah disimpan ke file " + cdfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 2. OBE
    public static double DeterminanOBE(Matriks matriks) {

        double determinant = 0;
        if(matriks.col != matriks.row){
            System.out.println("bukan matriks persegi!");
            return determinant;
        } else{
            // matriks adalah matriks persegi
            for(int i = 0; i < matriks.row; i++){
                if(matriks.rowZero(matriks, i)){
                    return 0;
                }
            }

            for(int j = 0; j < matriks.col; j++){
                if(matriks.colZero(matriks, j)){
                    return 0;
                }
            }

            int tuker = 0;
            int iNotZero = 0, jNotZero = 0;
            double pengali;
            boolean c;

            // conditional untuk ngecek apakah elemen pertama matriks != 0.
            // kalau != 0 maka tukar baris
            if(matriks.matrix[0][0] == 0){
                int iTuker = 1;
                for(int i = 1; i < matriks.row; i++){
                    if(matriks.matrix[i][0] != 0){
                        iTuker = i;
                        tuker++;
                        break;
                    }
                }
                //operasi tuker baris
                matriks.OBE(2, 0, 0, iTuker);
            }
            
            c = matriks.segitigaBawah(matriks);

            // while loop untuk operasi OBE sampai jadi matriks segitiga bawah
            while(c == false){
                // loop untuk cari indeks elemen segitiga bawah yang belum nol
                BigLoop:
                for(int j = 0; j < matriks.col; j++){
                    for(int i = 0; i < matriks.row; i++){
                        if(i > j){
                            if(matriks.matrix[i][j] != 0){
                                iNotZero = i;
                                jNotZero = j;
                                break BigLoop;
                            }
                        }
                    }
                }

                // pengali untuk operasi obe 3
                pengali = matriks.matrix[iNotZero][jNotZero] / matriks.matrix[jNotZero][jNotZero];
                
                // operasi obe 3
                matriks.OBE(3, iNotZero, -pengali, jNotZero);

                // pengecekan lagi apakah sudah matriks segitiga bawah
                c = matriks.segitigaBawah(matriks);
                matriks.printMatrix();
                System.out.println("\n");
            }

            // conditional untuk tuker (-1)^tuker
            if(tuker % 2 == 0){
                tuker = 1;
            } else{
                tuker = -1;
            }
            // looping untuk perkalian semua diagonal utama
            determinant = 1;
            for (int i = 0; i < matriks.col; i++){
                determinant *= matriks.matrix[i][i];
            }
            determinant *= tuker; 
            return determinant;
        }
    }

    // 3. Ekspansi Kofaktor
    public static double DeterminanKofaktor(Matriks matriks) {
        if (matriks.row == 2) {
            double ad = matriks.matrix[0][0] * matriks.matrix[1][1];
            double bc = matriks.matrix[0][1] * matriks.matrix[1][0];

            return ad - bc;
        } else {
            int i, j, k;
            double det = 0;

            for (i = 0; i < matriks.col; i++) {
                Matriks minorEntry = new Matriks(matriks.row - 1, matriks.col - 1);
                int mrow = 0;
                int mcol = 0;

                for (j = 1; j < matriks.row; j++) {
                    for (k = 0; k < matriks.col; k++) {
                        if (k != i) {
                            minorEntry.matrix[mrow][mcol] = matriks.matrix[j][k];
                            mrow = (mcol + 1 == matriks.col - 1) ? mrow + 1 : mrow;
                            mcol = (mcol + 1 == matriks.col - 1) ? 0 : mcol + 1;
                        }
                    }
                }

                double x = DeterminanKofaktor(minorEntry);
                double adder = matriks.matrix[0][i] * x;

                if (i % 2 != 0) {
                    adder = -1 * adder;
                }
                det += adder;
            }
            return det;
        }
    }
}
