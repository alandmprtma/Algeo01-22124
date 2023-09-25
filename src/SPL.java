public class SPL {
    // Methods
    public static void main(String[] args) {
        
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

    }
}
