import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Balikan {
    
    // Methods
    public static void main(String[] args) throws IOException {
        // Matriks MatriksX = BicubicSplineInterpolation.MatriksX();
        // MatriksX.printMatrix();

        // Matriks Invers = Balikan.BalikanAdjoin(MatriksX);
        // Invers.printMatrix();

        // Open scanner
        Scanner scanner = new Scanner(System.in);
        
        File file = new File("./test/tes.txt");
        Matriks matriks = Matriks.ReadMatrixFromFile(file);

        Matriks invers = BalikanAdjoin(matriks);
        invers.printMatrix();
        invers.SaveMatrixToFile("tes_result.txt");

        // Close scanner
        scanner.close();
    }

    // 1. Balikan Gauss-Jordan

    // 2. Balikan Adjoin
    public static Matriks BalikanAdjoin(Matriks matriks) {
        Matriks matKofaktor = Matriks.MatriksKofaktor(matriks);
        Matriks adjoin = Matriks.Transpose(matKofaktor);
        double determinan = Determinan.DeterminanKofaktor(matriks);

        int i, j;
        for (i = 0; i < adjoin.row; i++) {
            for (j = 0; j < adjoin.col; j++) {
                adjoin.matrix[i][j] /= determinan;
            }
        }

        return adjoin;
    }
}
