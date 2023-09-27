import java.io.File;
import java.util.Scanner;

public class SPL {
    // Methods
    public static void main(String[] args) {
        // File file = new File("../matriks_cramer.txt");
        // Matriks m = Matriks.ReadMatrixFromFile(file);
        // SPLCramer(m);
    }

    // 1. Gauss
    public static void SPLGauss(Matriks matriks) {
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
                System.out.println("Matriks tidak memiliki solusi!");
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
