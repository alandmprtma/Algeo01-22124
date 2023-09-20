public class Balikan {
    
    // Methods
    public static void main(String[] args) {
        
    }

    // 1. Balikan Gauss-Jordan
    // public static void BalikanGaussJordan(Matriks matriks) {

    // }

    // 2. Balikan Adjoin
    public static Matriks BalikanAdjoin(Matriks matriks) {
        Matriks matKofaktor = Matriks.MatriksKofaktor(matriks);
        Matriks adjoin = Matriks.Transpose(matKofaktor);
        float determinan = Determinan.DeterminanKofaktor(matriks);

        int i, j;
        for (i = 0; i < adjoin.row; i++) {
            for (j = 0; j < adjoin.col; j++) {
                adjoin.matrix[i][j] /= determinan;
            }
        }

        return adjoin;
    }
}
