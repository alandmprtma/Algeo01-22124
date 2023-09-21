import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Matriks {
    public static void main(String[] args) {
        // Create scanner
        Scanner scanner = new Scanner(System.in);


        File file = new File("./test/tes.txt");

        Matriks matriks = ReadMatrixFromFile(file);
        matriks.printMatrix();

        // Close scanner
        scanner.close();
    }
    
    // Attributes
    double[][] matrix;
    int row;
    int col;

    // Constructor
    Matriks(int r, int c) {
        this.matrix = new double[r][c];
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

        this.matrix = new double[row][col];
        this.row = row;
        this.col = col;
        
        int i;
        for (i = 0; i < row; i++) {
            int j = 0;
            for (j = 0; j < col; j++) {
                System.out.printf("Matriks[%d][%d] = ", i+1, j+1);
                this.matrix[i][j] = s.nextDouble();
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

    // 3. ReadMatrixFromFile
    public static Matriks ReadMatrixFromFile(File file) {
        try {
            Scanner scan = new Scanner(file);
            Scanner scanCopy = new Scanner(file);
            
            int rows, cols;
            rows = 0;
            cols = 0;

            while (scan.hasNextLine()) {
                String str = scan.nextLine();
                rows += 1;

                if (rows == 1) {
                    String[] firstLine = str.split(" ");
                    cols = firstLine.length;
                }
            }

            Matriks matriks = new Matriks(rows, cols);
            int i, j;
            for (i = 0; i < rows; i++) {
                String row = scanCopy.nextLine();
                String[] rowArray = row.split(" ");
                for (j = 0; j < cols; j++) {
                    matriks.matrix[i][j] = Double.parseDouble(rowArray[j]);
                }
            }

            scan.close();
            scanCopy.close();

            return matriks;
        } catch (FileNotFoundException e) {
            e.printStackTrace();

            return null;
        }
    }

    // 4. SaveMatrixToFile
    public void SaveMatrixToFile(String filename) throws IOException {        
        String matrixString = "";
        int i, j;
        for (i = 0; i < this.row; i++) {
            for (j = 0; j < this.col; j++) {
                matrixString += Double.toString(this.matrix[i][j]);
                matrixString += ((j == this.col - 1) && (i < this.row - 1)) ? "\n" : "|";
            }
        }

        FileWriter writer = new FileWriter("./test/" + filename);
        writer.write(matrixString);
        writer.close();
    }

    // 4. Multiply
    public static Matriks Multiply(Matriks a, Matriks b) {
        Matriks c = new Matriks(a.row, b.col);
        int i, j, k;
        double jumlah = 0;
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

    // 5. Transpose
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

    // 6. OBE
    public void OBE(int baris, int operasi) {

    }

    // 7. Kofaktor
    public double Kofaktor(int row, int col) {
        Matriks minorEntry = new Matriks(this.row - 1, this.col - 1);
        int mrow = 0;
        int mcol = 0;

        int i, j;
        for (i = 0; i < this.row; i++) {
            for (j = 0; j < this.col; j++) {
                if ((i != row) && (j != col)) {
                    minorEntry.matrix[mrow][mcol] = this.matrix[i][j];
                    mrow = (mcol + 1 == this.col - 1) ? mrow + 1 : mrow;
                    mcol = (mcol + 1 == this.col - 1) ? 0 : mcol + 1; 
                }
            }
        }

        double kofaktor = Determinan.DeterminanKofaktor(minorEntry);
        if ((row + col) % 2 != 0) {
            kofaktor *= -1;
        }
        return kofaktor;
    }

    // 8. Matriks Kofaktor
    public static Matriks MatriksKofaktor(Matriks matriks) {
        Matriks matKofaktor = new Matriks(matriks.row, matriks.col);

        int i, j;
        for (i = 0; i < matKofaktor.row; i++) {
            for (j = 0; j < matKofaktor.col; j++) {
                matKofaktor.matrix[i][j] = matriks.Kofaktor(i, j);
            }
        }

        return matKofaktor;
    }
}
