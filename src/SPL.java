import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class SPL {
    // Methods
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Matriks matriks = new Matriks(0, 0);
        matriks.readMatrix(scanner);
        SPLGauss(matriks);
    }

    // 1. PrintSPLtoFile
    public static void PrintSPLtoFile(double[] solusi) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Masukkan nama file untuk menyimpan solusi SPL : ");
            String cdfile;
            cdfile = scanner.nextLine();
            cdfile = "./output/" + cdfile + ".txt";
            BufferedWriter tulis = new BufferedWriter(new FileWriter(cdfile));
            tulis.write("Solusi SPL :\n");
            for (int i = 0; i < solusi.length; i++)
            {
                tulis.write("x" + (i+1) + " = " + solusi[i] + "\n");
            }
            tulis.close();
            scanner.close();
            System.out.println("Data telah disimpan ke file " + cdfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 2. PrintSolusiParametriktoFile
    public static void PrintParametriktoFile(int n, String[][] variabel) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Masukkan nama file untuk menyimpan solusi parametrik SPL : ");
            String cdfile;
            cdfile = scanner.nextLine();
            cdfile = "../output/" + cdfile + ".txt";
            BufferedWriter tulis = new BufferedWriter(new FileWriter(cdfile));
            tulis.write("Solusi Parametrik :\n");
            for (int i = 0; i < n; i++)
            {
                tulis.write("x" + i + " = " + variabel[i][0] + "\n");
            }
            tulis.close();
            scanner.close();
            System.out.println("Data telah disimpan ke file " + cdfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void PrintParametriktoFilejordan(String[] variabel) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Masukkan nama file untuk menyimpan solusi parametrik SPL : ");
            String cdfile;
            int n = variabel.length;
            cdfile = scanner.nextLine();
            cdfile = "../output/" + cdfile + ".txt";
            BufferedWriter tulis = new BufferedWriter(new FileWriter(cdfile));
            tulis.write("Solusi Parametrik :\n");
            for (int i = 0; i < n; i++)
            {
                tulis.write("x" + i + " = " + variabel[i] + "\n");
            }
            tulis.close();
            scanner.close();
            System.out.println("Data telah disimpan ke file " + cdfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 3. Gauss
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
        //Kasus SPL memiliki solusi banyak
        if (matriks.matrix[matriks.row-1][matriks.col-1] == 0 && matriks.matrix[matriks.row-1][matriks.col-2] == 0){
            System.out.println("Matriks memiliki solusi banyak!");
            String [][] tempvariable = new String[matriks.col-1][3];
            for (int i = 0; i < matriks.col - 1; i++)
            {
                int huruf =97 + i;
                char temphuruf = (char) huruf;
                tempvariable[i][0] = ""+temphuruf;
                tempvariable[i][1] = "1";
            }
            for (int i = 0; i < matriks.row; i++)
            {
                for(int j = 0; j < matriks.col-1; j++)
                {
                    if(matriks.matrix[i][j]!=0)
                    {
                        tempvariable[j][0]="";
                        tempvariable[j][1]="0";
                        tempvariable[j][2]=Double.toString(matriks.matrix[i][j]);
                        break;
                    }
                }
            }
            int count = matriks.col-2;
            for (int i = matriks.row-1; i >= 0; i--)
            {
                double nilaisementara = 0;
                if(tempvariable[count][1]=="0"){
                    nilaisementara = (matriks.matrix[i][matriks.col-1]/(Double.valueOf(tempvariable[count][2])));
                    int cek = 0;
                    for(int j = 0; j < matriks.col-1;j++)
                    {
                        if (j!=count){
                            if (matriks.matrix[i][j]!=0){
                                matriks.matrix[i][j] = matriks.matrix[i][j]*(-1);
                                if (tempvariable[j][1] == "0"){
                                    if((matriks.matrix[i][j]/(Double.valueOf((tempvariable[count][2]))))>0){
                                        nilaisementara = nilaisementara + ((matriks.matrix[i][j]/(Double.valueOf(tempvariable[count][2])))*Double.valueOf(tempvariable[j][0]));
                                    }
                                }
                                else{
                                    tempvariable[count][1] = "1";
                                    if (matriks.matrix[i][j]> 0 && tempvariable[count][0]!=""){
                                        tempvariable[count][0] = tempvariable[count][0] + " + ";
                                    }
                                    tempvariable[count][0] = tempvariable[count][0] + Double.toString(matriks.matrix[i][j]/(Double.valueOf(tempvariable[count][2]))) + tempvariable[j][0];
                                }
                            }
                        }
                    }
                    for(int k = 0;k<matriks.col-1;k++){
                        if(matriks.matrix[i][k]!=0){
                            cek = 1;
                        }
                    }
                    if (cek == 0){
                        count ++;
                    }
                    else if(nilaisementara<0){
                        tempvariable[count][0] = ""+tempvariable[count][0] + " - " + Double.toString(Math.abs(nilaisementara)); 
                    }
                    else if(nilaisementara != 0 || (nilaisementara == 0 && tempvariable[count][0] == "")){
                        tempvariable[count][0] = ""+tempvariable[count][0] + " + " + Double.toString(Math.abs(nilaisementara)); 
                    }
                    count--;
                }
                else{
                    count--;
                    i++;
                }
            }
            System.out.println("Solusi Parametrik :");
            for(int i = 0; i < matriks.col-1; i++){
                System.out.println("x" + i + " = " + tempvariable[i][0]);
            }
            PrintParametriktoFile(matriks.col-1, tempvariable);
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
            System.out.println("Solusi SPL :");
            for (int i = 0; i < matriks.row; i++) {
                System.out.println("X"+(i+1)+" = "+solusi[i]);
            }
            PrintSPLtoFile(solusi);
        }
    }

    // 4. Gauss-Jordan
    public static void SPLGaussJordan(Matriks matriks){
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

        //Sudah terbentuk Matriks Eselon Baris
        //Mengubah menjadi matriks eselon baris tereduksi
        for (int i = matriks.row - 1; i >=0; i--){
            boolean found = false; // untuk mencari elemen satu utama
            int j = 0;
            int jOne = -1;
            while(found == false && j < matriks.col - 1){
                if(matriks.matrix[i][j] == 1){
                    found = true;
                    jOne = j;
                }
                j++;
            }
            if(found == true){
                for(int k = i-1; k>=0; k--){
                    if(matriks.matrix[k][jOne] != 0){
                        double pengali = matriks.matrix[k][jOne] / matriks.matrix[i][jOne];
                        matriks.OBE(3, k, -pengali, i);
                    }
                }
            }
        }

        // sudah terbentuk matriks eselon baris tereduksi
        // akan ditentukan solusi spl
        
        if(matriks.matrix[matriks.row - 1][matriks.col - 1] != 0 && matriks.matrix[matriks.row - 1][matriks.col - 2] == 0){
            // kasus tidak ada solusi
            System.out.println("tidak ada solusi!");

        } else if((matriks.matrix[matriks.row - 1][matriks.col - 1] == 0 && matriks.matrix[matriks.row - 1][matriks.col - 2] == 0) || (matriks.col-1 > matriks.row)){
            // kasus solusi banyak
            System.out.println("solusi banyak!");
            String[] nilaiX = new String[matriks.col-1];
            for(int i = matriks.row-1; i >= 0; i--){
                boolean satu = false; // untuk pencarian elemen satu utama
                boolean only = false;// untuk pengecekan apakah hanya satu elemen di baris tsb yg bukan nol
                int j = 0; 
                int jOne = -1;
                while(!satu && j < matriks.col - 1){
                    if(matriks.matrix[i][j] == 1){
                        satu = true;
                        jOne = j;
                    }
                    j++;
                }
                if(satu){
                    only = true;
                    for(int k = jOne + 1; k < matriks.col - 1; k++){
                        if(matriks.matrix[i][k] != 0){
                            only = false;
                        }
                    }
                }

                // kalau satu utama adalah satu-satunya elemen pada baris tsb maka simpan value augmented
                if(only){
                    nilaiX[jOne] = ""+Double.toString(matriks.matrix[i][matriks.col-1]);
                }
            }

            // looping untuk mengganti nilai x dengan variavel a,b,c,...
            for(int i = 0; i < matriks.col-1; i++){
                if(nilaiX[i] == null){
                    nilaiX[i] = "" + ((char)(97+i));
                }
            }

            
            for(int i = 0; i < matriks.col-1; i++){
                System.out.println("nilai: "+nilaiX[i]);
            }

            //langkah untuk menentukan nilai x yang punya elemen satu utama
            for(int i = 0; i < matriks.row; i++){
                boolean satu = false; // untuk pencarian elemen satu utama
                boolean only = false;// untuk pengecekan apakah hanya satu elemen di baris tsb yg bukan nol
                int j = 0; 
                int jOne = -1;
                while(!satu && j < matriks.col - 1){
                    if(matriks.matrix[i][j] == 1){
                        satu = true;
                        jOne = j;
                    }
                    j++;
                }
                if(satu){
                    only = true;
                    for(int k = jOne + 1; k < matriks.col - 1; k++){
                        if(matriks.matrix[i][k] != 0){
                            only = false;
                        }
                    }
                }

                // kalau satu utama tapi di belakangnya masih ada angka selain nol
                if(!only&&jOne != -1){
                    System.out.print("x"+(jOne + 1)+" = ");
                    if(matriks.matrix[i][matriks.col-1] != 0){
                        System.out.print(matriks.matrix[i][matriks.col-1]);
                    }
                    for(int k = jOne+1; k < matriks.col-1; k++){
                        if(matriks.matrix[i][k] > 0){
                            System.out.print(" "+-(matriks.matrix[i][k])+nilaiX[k]);
                        } else if(matriks.matrix[i][k] < 0){
                            System.out.print(" +"+-(matriks.matrix[i][k])+nilaiX[k]);
                        }
                    }
                    System.out.println();
                }
            }
            for(int i = 0; i < matriks.col - 1; i++){
                System.out.println("x"+(i+1)+" = "+nilaiX[i]);
            }
            PrintParametriktoFilejordan(nilaiX);
            
        } else{
            // kasus solusi unik
            System.out.println("solusi unik");
            double[] nilai = new double[matriks.row];
            for(int i = 0; i < matriks.row; i++){
                System.out.println("x"+(i+1)+": "+matriks.matrix[i][matriks.col-1]);
                nilai[i] = matriks.matrix[i][matriks.col-1];
            }
            PrintSPLtoFile(nilai);
        }
    }

    // 5. Matriks Balikan
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

    // 6. Cramer
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
                double[] x = new double[matriks_utama.col];
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
                //Cetak Solusi
                x[i] = determinancramer/determinanmatriks;
                System.out.print("x" + (i+1) + " = " + x[i] +"\n");          
                }
                PrintSPLtoFile(x);
            }
        }
        else{
            System.out.println("Matriks tidak valid karena bukan merupakan matriks augmented!");
        }
    }
}
