import java.util.Arrays;
import java.util.Scanner;

public class PolynomInterpolation {
    public static void main(String[] args) {
        PolinomInterpolasi();
    }

    public static void readPolinom(Scanner s, int n, double[]varx, double[]vary, double x) {
        System.out.println("======== Mengisi derajat polinom (n) ========");
        
        System.out.print("Orde Polinom (n) : ");
        n = s.nextInt();

        varx = new double[n+1];
        vary = new double[n+1];

        System.out.println("======== Masukkan "+(n+1)+" buah titik data ========");
        int i;
        for (i = 0; i <= n; i++) {
            System.out.printf("X%d = ", i);
            varx[i] = s.nextDouble();
            System.out.printf("Y%d = ", i);
            vary[i] = s.nextDouble();
        }
        System.out.print("Masukkan nilai x yang akan ditaksir menggunakan fungsi interpolasi : ");
        x = s.nextDouble();
    }

    public static void PolinomInterpolasi(){
        try (Scanner scanner = new Scanner(System.in)) {
            // Kode Anda di sini
            /*double[] varx,vary;
            varx = new double[0];
            vary = new double[0];
            int n = 0;
            double x = 0.0;
            readPolinom(scanner, n, varx, vary, x);*/
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
            for (i = 0; i < n+1; i++)
            {
                y += solusi[i]*Math.pow(x,i);
            }
            y = Math.round(y * 1000000.0) / 1000000.0;
            System.out.println("nilai y yang telah ditaksir menggunakan fungsi polinom adalah "+y+".");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void SolusiGauss(Matriks matriks, double[] solusi) {
        //Proses Pertukaran Baris
        //Cek berapa banyak kosong dalam baris
        int[] cekkosong;
        cekkosong = new int[matriks.row];
        
        for (int i = 0;i<matriks.row;i++)
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
        
        for (int i = 0;i<matriks.row;i++)
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
        for (int i = 0;i<matriks.row;i++)
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
        for (int i = 0; i<matriks.row; i++)
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
        for (int i = 0; i < matriks.row; i++)
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
        for (int i = 0;i<matriks.row;i++)
        {
            for (int j = 0;j<matriks.col;j++)
            {
                matriks.matrix[i][j] = Math.round(matriks.matrix[i][j] * 1000000.0) / 1000000.0;
            }
        }

        //Terbentuk Matriks Eselon Baris
        //Lakukan pengecekan apakah sistem persamaan linear memiliki solusi unik, solusi banyak, atau tidak memiliki solusi.
        matriks.printMatrix();
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
            for (int i = matriks.row-1; i>=0; i--){
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
    }

}
