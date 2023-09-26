import java.io.File;
import java.util.Scanner;

public class SPL {
    // Methods
    public static void main(String[] args) {
        File file = new File("../matriks_cramer.txt");
        Matriks m = Matriks.ReadMatrixFromFile(file);
        SPLCramer(m);
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
        if (matriks.row == matriks.col)
        {
            //define determinan matriks
            double determinanmatriks = Determinan.DeterminanKofaktor(matriks);
            if (determinanmatriks == 0)
            {
                System.out.println("Matriks tidak memiliki solusi!");
            }
            else
            {
                 //create scanner
                Scanner scanner = new Scanner(System.in);

                Matriks equation = new Matriks(matriks.row, 1);
                equation.readMatrix(scanner);
                Matriks cramer = new Matriks(matriks.row, matriks.col);
                for (int i = 0; i < matriks.col; i++) 
                {
                    for (int j = 0; j < matriks.row; j++)
                    {
                        for (int k = 0; k < matriks.col; k++)
                        {
                            if (i == k)
                            {
                                cramer.matrix[j][k] = equation.matrix[j][0];
                            }
                            else{
                                cramer.matrix[j][k] = matriks.matrix[j][k];
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
            System.out.println("Matriks tidak valid karena bukan merupakan matriks persegi!");
        }

    }
}
