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

    // 1. OBE
    // public static double DeterminanOBE(Matriks matriks) {

    // }

    // 2. Ekspansi Kofaktor
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
