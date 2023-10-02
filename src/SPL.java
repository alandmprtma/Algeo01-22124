import java.io.File;
import java.util.Arrays;

public class SPL {
    // Methods
    public static void main(String[] args) {
        File file = new File("../test/matriks_cramer.txt");
        Matriks m = Matriks.ReadMatrixFromFile(file);
        SPLGauss(m);
    }

    // 1. Gauss
    public static void SPLGauss(Matriks matriks) {
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
            double[] solusi = new double[matriks.row];
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

            //Cetak solusi
            for (int i = 0; i < matriks.row; i++) {
                System.out.println("X"+(i+1)+" = "+solusi[i]);
            }
        }
    }

    // 2. Gauss-Jordan

    // 3. Matriks Balikan
    public static void SPLBalikan(Matriks matriks) {
        Matriks coefficients = new Matriks(matriks.row, matriks.col - 1);
        Matriks constants = new Matriks(matriks.row, 1);

        int i, j;
        for (i = 0; i < matriks.row; i++) {
            for (j = 0; j < matriks.col; j++) {
                if (j == matriks.col - 1) {
                    constants.matrix[i][0] = matriks.matrix[i][j];
                } else {
                    coefficients.matrix[i][j] = matriks.matrix[i][j];
                }
            }
        }

        Matriks inverse = Balikan.BalikanAdjoin(coefficients);
        Matriks results = Matriks.Multiply(inverse, constants);

        results.printMatrix();
    }

    // 4. Cramer
    public static void SPLCramer(Matriks matriks) {
        if (matriks.row == matriks.col - 1)
        {
            //define determinan matriks
            Matriks matriks_utama = new Matriks(matriks.row, matriks.col-1);
            for (int i = 0; i < matriks.row; i++) {
                for (int j = 0; j < matriks.col-1; j++) {
                    matriks_utama.matrix[i][j] = matriks.matrix[i][j];
                }
            }
            double determinanmatriks = Determinan.DeterminanKofaktor(matriks_utama);
            if (determinanmatriks == 0)
            {
                System.out.println("Matriks tidak memiliki solusi atau matriks memiliki solusi banyak!");
            }
            else
            {
                 //create scanner
                Matriks equation = new Matriks(matriks.row, 1);
                for (int i = 0; i < matriks.row; i++) {
                    equation.matrix[i][0] = matriks.matrix[i][matriks.col-1];                    
                }
                Matriks cramer = new Matriks(matriks_utama.row, matriks_utama.col);
                for (int i = 0; i < matriks_utama.col; i++) 
                {
                    for (int j = 0; j < matriks_utama.row; j++)
                    {
                        for (int k = 0; k < matriks_utama.col; k++)
                        {
                            if (i == k)
                            {
                                cramer.matrix[j][k] = equation.matrix[j][0];
                            }
                            else{
                                cramer.matrix[j][k] = matriks_utama.matrix[j][k];
                            }
                        }
                    }
                double determinancramer = Determinan.DeterminanKofaktor(cramer);
                double x = determinancramer/determinanmatriks;
                System.out.print("x" + (i+1) + " = " + x +"\n");          
                }
            }
        }
        else{
            System.out.println("Matriks tidak valid karena bukan merupakan matriks augmented!");
        }
    }
}
