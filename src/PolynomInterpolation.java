import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;

public class PolynomInterpolation {
    public static void main(String[] args) {
        PolinomInterpolasiFile();
    }

    // Driver
    public static void PolynomInterpolationDriver(Scanner scanner) {
        App.slowprint("Interpolasi Polinom");
        App.slowprint("\nPilih metode untuk input: ");
        
        String[] input = {
            "1. Input melalui terminal", 
            "2. Input dari suatu file .txt"
        };
        App.printMenu(input);

        int choice = App.askInput(1, 2, scanner);

        if (choice == 1) {
            PolinomInterpolasiKey();
        } else {
            PolinomInterpolasiFile();
        }
    }




    // 1. PrintPolinomToFile
    public static void PrintPolinomtoFile(double y, Scanner scanner, String fx) {
        try {
            System.out.print("Masukkan nama file untuk menyimpan hasil interpolasi : ");
            scanner.nextLine();
            String cdfile;
            cdfile = scanner.nextLine();
            cdfile = "../output/" + cdfile + ".txt";
            BufferedWriter tulis = new BufferedWriter(new FileWriter(cdfile));
            for (int i = 0; i < 1; i++)
            {
                tulis.write("Persamaan interpolasi polinomial :\n");
                tulis.write(fx+"\n");
                tulis.write("nilai y yang telah ditaksir menggunakan fungsi polinom adalah "+y+".");
            }
            tulis.close();
            System.out.println("Data telah disimpan ke file " + cdfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 2. Polinom Interpolasi menggunakan masukan keyboard user
    public static void PolinomInterpolasiKey(){
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("======== Mengisi derajat polinom (n) ========");
        
            System.out.print("Orde Polinom (n) : ");
            int n = scanner.nextInt();
            double [] varx, vary;
            varx = new double[n+1];
            vary = new double[n+1];

            System.out.println("======== Masukkan "+(n+1)+" buah titik data ========");
            int i;
            for (i = 0; i <= n; i++) {
                System.out.printf("X%d = ", i);
                varx[i] = scanner.nextDouble();
                System.out.printf("Y%d = ", i);
                vary[i] = scanner.nextDouble();
            }
            System.out.print("Masukkan nilai x yang akan ditaksir menggunakan fungsi interpolasi : ");
            double x = scanner.nextDouble();
            //Bentuk Matriks Augmented
            Matriks matriks = new Matriks(n+1, n+2);
            for (i = 0; i < n+1; i++){
                for (int j = 0; j < n+2; j++){
                    if (j == n+1)
                    {
                        matriks.matrix[i][j] = vary[i];
                    }
                    else if (j < n+1 && i < n+1)
                    {
                        matriks.matrix[i][j] = Math.pow(varx[i],j);
                    }
                }
            }
            //Terbentuk matriks augmented
            double[] solusi = new double[n+1];
            //Dilakukan pembatasan 3 angka desimal untuk menghilangkan unsignificant number pada proses eliminasi
            for (i = 0; i < matriks.row; i++)
            {
                for (int j = 0; j < matriks.col; j++)
                {
                    matriks.matrix[i][j] = Math.round(matriks.matrix[i][j]*1000.0) / 1000.0;
                }
            }
            //Proses Pertukaran Baris
            //Cek berapa banyak kosong dalam baris
            int[] cekkosong;
            cekkosong = new int[matriks.row];
            
            for (i = 0;i<matriks.row;i++)
            {
                cekkosong[i]=0;
                int j = 0;
                while(j<matriks.col && matriks.matrix[i][j] == 0)
                {
                    cekkosong[i]++;
                    j++;
                }
            }
            int[] temp;
            temp = new int[matriks.row];
            
            for (i = 0;i<matriks.row;i++)
            {
                temp[i]=0;
                int j = 0;
                while(j<matriks.col && matriks.matrix[i][j] == 0)
                {
                    temp[i]++;
                    j++;
                }
            }
            Arrays.sort(temp);
            
            // urutin baris dari yang 0 nya paling dikit atau ga ada
            for (i = 0;i<matriks.row;i++)
            {
                if (cekkosong[i] != temp[i])
                {
                    int k = i + 1;
                    boolean check = false;
                    while (check == false && k < matriks.row)
                    {
                        if (cekkosong[i] == temp[k])
                        {
                            check = true;
                            matriks.OBE(2,i,-1,k);
                        }
                        k++;
                    }
                }
            }

            //Lakukan eliminasi Gauss pada kolom i
            for (i = 0; i<matriks.row; i++)
            {
                int j = i;
                while (j < matriks.col-1 && matriks.matrix[i][j] == 0)
                {
                    j++;
                }
                if (j < matriks.col -1)
                {
                    double konstanta = 1/matriks.matrix[i][j]; 
                    matriks.OBE(1, i, konstanta, -1);
                    for (int k = i+1; k<matriks.row; k++)
                    {
                        if (matriks.matrix[k][j] != 0)
                        {
                            matriks.OBE(3,k,(-1)*matriks.matrix[k][j],i);
                        }
                    }
                }
            }
            // Mendeteksi Minus Zero dan mengubah ke zero
            for (i = 0; i < matriks.row; i++)
            {
                for (int j = 0; j < matriks.col; j++)
                {
                    if (matriks.matrix[i][j] == -0.0)
                    {
                        matriks.matrix[i][j] = 0.0;
                    }
                }
            }
            //Pembulatan elemen matriks
            for (i = 0;i<matriks.row;i++)
            {
                for (int j = 0;j<matriks.col;j++)
                {
                    matriks.matrix[i][j] = Math.round(matriks.matrix[i][j] * 1000000.0) / 1000000.0;
                }
            }

            //Terbentuk Matriks Eselon Baris
            //Lakukan pengecekan apakah sistem persamaan linear memiliki solusi unik, solusi banyak, atau tidak memiliki solusi.
            //Kasus SPL memiliki solusi banyak
            if (matriks.matrix[matriks.row-1][matriks.col-1] == 0 && matriks.matrix[matriks.row-1][matriks.col-2] == 0){
                System.out.println("Matriks memiliki solusi banyak!");
            }
            //Kasus SPL tidak memiliki solusi
            else if (matriks.matrix[matriks.row-1][matriks.col-1] != 0 && matriks.matrix[matriks.row-1][matriks.col-2] == 0){
                System.out.println("Matriks tidak memiliki solusi!");
            }
            //Kasus SPL memiliki solusi unik
            else
            {
                //Proses substitusi mundur atau penyulihan mundur
                solusi = new double[matriks.row];
                for (i = matriks.row-1; i>=0; i--){
                    double sum = 0.0;
                    for (int j = i+1; j < matriks.row; j++){
                        sum += matriks.matrix[i][j] * solusi[j];
                    }
                    if (matriks.matrix[i][i] != 0)
                    {
                        solusi[i] = (matriks.matrix[i][matriks.row] - sum) / matriks.matrix[i][i];
                        solusi[i] = Math.round(solusi[i] * 1000000.0) / 1000000.0;
                    }
                }
            }
            //Mentaksir nilai y berdasarkan masukkan x dengan fungsi interpolasi
            //int i;
            double y = 0.0;
            String fungsi = "f(x) = ";
            for (i = 0; i < n; i++)
            {
                solusi[i] = Math.round(solusi[i] * 1000000.0) / 1000000.0;
                if (i == 0)
                {
                    fungsi += Double.toString(solusi[i]);
                }
                else if (i == 1 && solusi[i] > 0)
                {
                    fungsi += " + " + Double.toString(solusi[i]) + "X";
                }
                else if (i == 1 && solusi[i] < 0)
                {
                    fungsi += " " + Double.toString(solusi[i]) + "X";
                }
                else if (solusi[i] == 0)
                {
                    fungsi += "";
                }
                else if (solusi[i] < 0)
                {
                    fungsi += " " + Double.toString(solusi[i]) + "X^" + Integer.toString(i);
                } 
                else if (solusi[i] > 0)
                {
                    fungsi += " + " + Double.toString(solusi[i]) + "X^" + Integer.toString(i);
                }
            }
            for (i = 0; i < n+1; i++)
            {
                y += solusi[i]*Math.pow(x,i);
            }
            y = Math.round(y * 1000000.0) / 1000000.0;
            System.out.print("Persamaan interpolasi polinomial :\n");
            System.out.println(fungsi);
            System.out.println("nilai y yang telah ditaksir menggunakan fungsi polinom adalah "+y+".");
            PrintPolinomtoFile(y, scanner, fungsi);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Matriks bacaFileInterpolasi(Scanner scanner){
        try {
            System.out.print("Masukkan nama file yang ingin diinterpolasi: ");
            String filename = scanner.next();
            filename = "../test/" + filename + ".txt";
            File file = new File(filename);
            while(!file.exists()){
                System.out.print("File tidak ditemukan! Mohon masukkan kembali nama file: ");
                filename = scanner.next();
                filename = "../test/" + filename + ".txt";
                file = new File(filename);
            }
            BufferedReader read = new BufferedReader(new FileReader(filename));
            String line;
            int row = 0;
            int col = 2;
            while ((line = read.readLine()) != null)
            {
                row ++;
            }
            read.close();
            read = new BufferedReader(new FileReader(filename));

            Matriks matriks = new Matriks(row, col);
            int indeksBaris = 0;
            while ((line = read.readLine()) != null)
            {
                String[] baris = line.split(" ");
                if (indeksBaris != row-1)
                {
                    for (int j = 0; j<matriks.col; j++)
                    {
                        matriks.matrix[indeksBaris][j] = Double.parseDouble(baris[j]);
                    }
                    indeksBaris++;
                }
                else
                {
                    matriks.matrix[indeksBaris][0] = Double.parseDouble(baris[0]);
                }
            }
            return matriks;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 3. Polinom Interpolasi menggunakan file external
    public static void PolinomInterpolasiFile(){
        try (Scanner scanner = new Scanner(System.in)) {
            // Kode Anda di sini
            System.out.print("======== Mengisi derajat polinom (n) ========\n");
            Matriks matriks = new Matriks(0, 0);
            matriks = bacaFileInterpolasi(scanner);
            int i;
            double []varx = new double[matriks.row-1];
            double []vary = new double[matriks.row-1];
            for (i = 0; i < matriks.row-1; i++) {
                varx[i] = matriks.matrix[i][0];
                vary[i] = matriks.matrix[i][1];
            }
            double x = matriks.matrix[matriks.row-1][0];
            //Bentuk Matriks Augmented
            int n = matriks.row - 1;
            Matriks augmented = new Matriks(n, n+1);
            for (i = 0; i < n; i++){
                for (int j = 0; j < n+1; j++){
                    if (j == n)
                    {
                        augmented.matrix[i][j] = vary[i];
                    }
                    else if (j < n && i < n)
                    {
                        augmented.matrix[i][j] = Math.pow(varx[i],j);
                    }
                }
            }
            //Terbentuk matriks augmented
            double[] solusi = new double[n+1];
            //Dilakukan pembatasan 3 angka desimal untuk menghilangkan unsignificant number pada proses eliminasi
            for (i = 0; i < augmented.row; i++)
            {
                for (int j = 0; j < augmented.col; j++)
                {
                    augmented.matrix[i][j] = Math.round(augmented.matrix[i][j]*1000.0) / 1000.0;
                }
            }
            //Proses Pertukaran Baris
            //Cek berapa banyak kosong dalam baris
            int[] cekkosong;
            cekkosong = new int[augmented.row];
            
            for (i = 0;i<augmented.row;i++)
            {
                cekkosong[i]=0;
                int j = 0;
                while(j<augmented.col && augmented.matrix[i][j] == 0)
                {
                    cekkosong[i]++;
                    j++;
                }
            }
            int[] temp;
            temp = new int[augmented.row];
            
            for (i = 0;i<augmented.row;i++)
            {
                temp[i]=0;
                int j = 0;
                while(j<augmented.col && augmented.matrix[i][j] == 0)
                {
                    temp[i]++;
                    j++;
                }
            }
            Arrays.sort(temp);
            
            // urutin baris dari yang 0 nya paling dikit atau ga ada
            for (i = 0;i<augmented.row;i++)
            {
                if (cekkosong[i] != temp[i])
                {
                    int k = i + 1;
                    boolean check = false;
                    while (check == false && k < matriks.row)
                    {
                        if (cekkosong[i] == temp[k])
                        {
                            check = true;
                            augmented.OBE(2,i,-1,k);
                        }
                        k++;
                    }
                }
            }

            //Lakukan eliminasi Gauss pada kolom i
            for (i = 0; i<augmented.row; i++)
            {
                int j = i;
                while (j < augmented.col-1 && augmented.matrix[i][j] == 0)
                {
                    j++;
                }
                if (j < augmented.col -1)
                {
                    double konstanta = 1/augmented.matrix[i][j]; 
                    augmented.OBE(1, i, konstanta, -1);
                    for (int k = i+1; k<augmented.row; k++)
                    {
                        if (augmented.matrix[k][j] != 0)
                        {
                            augmented.OBE(3,k,(-1)*augmented.matrix[k][j],i);
                        }
                    }
                }
            }
            // Mendeteksi Minus Zero dan mengubah ke zero
            for (i = 0; i < augmented.row; i++)
            {
                for (int j = 0; j < augmented.col; j++)
                {
                    if (augmented.matrix[i][j] == -0.0)
                    {
                        augmented.matrix[i][j] = 0.0;
                    }
                }
            }
            //Pembulatan elemen matriks
            for (i = 0;i<augmented.row;i++)
            {
                for (int j = 0;j<augmented.col;j++)
                {
                    augmented.matrix[i][j] = Math.round(augmented.matrix[i][j] * 1000000.0) / 1000000.0;
                }
            }

            //Terbentuk Matriks Eselon Baris
            //Lakukan pengecekan apakah sistem persamaan linear memiliki solusi unik, solusi banyak, atau tidak memiliki solusi.
            //Kasus SPL memiliki solusi banyak
            if (augmented.matrix[augmented.row-1][augmented.col-1] == 0 && augmented.matrix[augmented.row-1][augmented.col-2] == 0){
                System.out.println("Matriks memiliki solusi banyak!");
            }
            //Kasus SPL tidak memiliki solusi
            else if (augmented.matrix[augmented.row-1][augmented.col-1] != 0 && augmented.matrix[augmented.row-1][augmented.col-2] == 0){
                System.out.println("Matriks tidak memiliki solusi!");
            }
            //Kasus SPL memiliki solusi unik
            else
            {
                //Proses substitusi mundur atau penyulihan mundur
                solusi = new double[augmented.row];
                for (i = augmented.row-1; i>=0; i--){
                    double sum = 0.0;
                    for (int j = i+1; j < augmented.row; j++){
                        sum += augmented.matrix[i][j] * solusi[j];
                    }
                    if (augmented.matrix[i][i] != 0)
                    {
                        solusi[i] = (augmented.matrix[i][augmented.row] - sum) / augmented.matrix[i][i];
                        solusi[i] = Math.round(solusi[i] * 1000000.0) / 1000000.0;
                    }
                }
            }
            //Mentaksir nilai y berdasarkan masukkan x dengan fungsi interpolasi
            //int i;
            double y = 0.0;
            String fungsi = "f(x) = ";
            for (i = 0; i < n; i++)
            {
                solusi[i] = Math.round(solusi[i] * 1000000.0) / 1000000.0;
                if (i == 0)
                {
                    fungsi += Double.toString(solusi[i]);
                }
                else if (i == 1 && solusi[i] > 0)
                {
                    fungsi += " + " + Double.toString(solusi[i]) + "X";
                }
                else if (i == 1 && solusi[i] < 0)
                {
                    fungsi += " " + Double.toString(solusi[i]) + "X";
                }
                else if (solusi[i] == 0)
                {
                    fungsi += "";
                }
                else if (solusi[i] < 0)
                {
                    fungsi += " " + Double.toString(solusi[i]) + "X^" + Integer.toString(i);
                } 
                else if (solusi[i] > 0)
                {
                    fungsi += " + " + Double.toString(solusi[i]) + "X^" + Integer.toString(i);
                }
            }
            for (i = 0; i < n; i++)
            {
                y += solusi[i]*Math.pow(x,i);
            }
            y = Math.round(y * 1000000.0) / 1000000.0;
            System.out.print("Persamaan interpolasi polinomial :\n");
            System.out.print(fungsi + "\n");
            System.out.print("nilai y yang telah ditaksir menggunakan fungsi polinom adalah "+y+".\n");
            PrintPolinomtoFile(y,scanner, fungsi);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}