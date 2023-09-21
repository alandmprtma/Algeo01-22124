public class SPL {
    // Methods
    public static void main(String[] args) {
        
    }

    // 1. Gauss
    public static void SPLGauss(Matriks matriks) {
    }

    // 2. Gauss-Jordan
    public static void SPLGaussJordan(Matriks matriks) {
        int j;
        for (j = 0; j < matriks.col - 1; j++) {
            double divisor = 1 / (double) matriks.matrix[j][j];
            matriks.OBE(1, j, divisor, -1);

            int i;
            for (i = 0; i < matriks.row; i++) {
                if (i != j) {
                    double adder = -1 * matriks.matrix[i][j];
                    matriks.OBE(3, i, adder, j);
                }
            }
            matriks.printMatrix();
            System.out.println();
        }
        int a;
        for (a = 0; a < matriks.row; a++) {
            matriks.OBE(3, a, 0, 1);
        }
        matriks.printMatrix();
    }

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

    }
}
