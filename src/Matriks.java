import java.util.*;

public class Matriks {
    
    // Attributes
    float[][] matrix;
    int row;
    int col;

    // Constructor
    Matriks(int r, int c) {
        this.matrix = new float[r][c];
    }

    // Methods

    // 1. ReadMatrix
    public void readMatrix() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("======== Mengisi matriks row x col ========");
        
        System.out.print("row: ");
        int row = scanner.nextInt();

        System.out.print("col: ");
        int col = scanner.nextInt();

        this.matrix = new float[row][col];
        this.row = row;
        this.col = col;

        int i;
        for (i = 0; i < row; i++) {
            int j = 0;
            for (j = 0; j < col; j++) {
                System.out.printf("Matriks[%d][%d] = ", i+1, j+1);
                this.matrix[i][j] = scanner.nextFloat();
            }
        }
        
        System.out.print("\n");
        
        // Close scanner
        scanner.close();
     }

    // 2. PrintMatrix
    public void printMatrix() {
        int i;
        for (i = 0; i < this.row; i++) {
            int j;
            System.out.print("[");
            for (j = 0; j < this.col; j++) {
                System.out.print(this.matrix[i][j]);
                if (j != this.col - 1) {
                    System.out.print(" ");
                } else {
                    System.out.print("]\n");
                }
            }
        }
    }

    // 3. Multiply
    public static Matriks Multiply(Matriks a, Matriks b) {
        Matriks c = new Matriks(a.row, b.col);

        return c;
    }

    // 4. Transpose
    public static Matriks Transpose(Matriks matriks) {
        Matriks transposeMatrix = new Matriks(matriks.col, matriks.row);

        return transposeMatrix;
    }

    // 5. OBE
    public void OBE(int baris, int operasi) {

    }

    // 6. Matriks Kofaktor
    public static Matriks Kofaktor(Matriks matriks) {
        Matriks matriksKofaktor = new Matriks(matriks.row, matriks.col);

        return matriksKofaktor;
    }

    public static void main(String[] args) {
        Matriks m = new Matriks(0, 0);
        m.readMatrix();
        m.printMatrix();
    }
}
