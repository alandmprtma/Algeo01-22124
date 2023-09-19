import java.util.*;

public class Matriks {
    
    // Attributes
    float[][] matrix;
    int row;
    int col;

    // Constructor
    Matriks(int r, int c) {
        this.matrix = new float[r][c];
        this.row = r;
        this.col = c;
    }

    // Methods

    // 1. ReadMatrix
    public void readMatrix(Scanner s) {
        System.out.println("======== Mengisi matriks row x col ========");
        
        System.out.print("row: ");
        int row = s.nextInt();

        System.out.print("col: ");
        int col = s.nextInt();

        this.matrix = new float[row][col];
        this.row = row;
        this.col = col;
        
        int i;
        for (i = 0; i < row; i++) {
            int j = 0;
            for (j = 0; j < col; j++) {
                System.out.printf("Matriks[%d][%d] = ", i+1, j+1);
                this.matrix[i][j] = s.nextFloat();
            }
        }
        
        System.out.print("\n");
        
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
        int i, j, k;
        float jumlah = 0;
        for (i = 0; i < a.row; i++)
        {
            for (j = 0; j < b.col; j++)
            {
                for (k = 0; k < b.row; k++)
                {
                    jumlah = jumlah + a.matrix[i][k]*b.matrix[k][j];
                }
                c.matrix[i][j] = jumlah;
                jumlah = 0;
            }
        }
        return c;
    }

    // 4. Transpose
    public static Matriks Transpose(Matriks matriks) {
        Matriks transposeMatrix = new Matriks(matriks.col, matriks.row);
        int i, j;
        for (i = 0; i < matriks.row; i++)
        {
            for (j = 0; j < matriks.col; j++)
            {
                transposeMatrix.matrix[j][i] = matriks.matrix[i][j];
            }
        }
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
        // Create scanner
        Scanner scanner = new Scanner(System.in);
        
        Matriks m = new Matriks(0, 0);
        m.readMatrix(scanner);
        m.printMatrix();

        Matriks n = new Matriks(0, 0);
        n.readMatrix(scanner);
        n.printMatrix();

        Matriks o = Multiply(m, n);
        o.printMatrix();

        // Close scanner
        scanner.close();
    }
}
