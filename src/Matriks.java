import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Matriks {
    public static void main(String[] args) throws IOException {
        // Create scanner
        Scanner scanner = new Scanner(System.in);

        // Operasi = 1 -> Kalikan baris dengan k
        // Operasi = 2 -> Tukar baris dengan baris2
        // Operasi = 3 -> Tambahkan baris dengan k * baris2

        // File file = new File("./test/tesOBE.txt");
        // Matriks matriks = ReadMatrixFromFile(file);

        // System.out.print("Operasi: ");
        // int operasi = scanner.nextInt();
        // int baris, k, baris2;
        // while (operasi != 0) {
        //     if (operasi == 1) {
        //         System.out.print("Baris: ");
        //         baris = scanner.nextInt();

        //         System.out.print("k: ");
        //         k = scanner.nextInt();

        //         matriks.OBE(1, baris - 1, k, -1);
        //     } else if (operasi == 2) {
        //         System.out.print("Baris 1: ");
        //         baris = scanner.nextInt();

        //         System.out.print("Baris 2: ");
        //         baris2 = scanner.nextInt();

        //         matriks.OBE(2, baris - 1, 1, baris2 - 1);
        //     } else if (operasi == 3) {
        //         System.out.print("Baris 1: ");
        //         baris = scanner.nextInt();

        //         System.out.print("Baris 2: ");
        //         baris2 = scanner.nextInt();

        //         System.out.print("k: ");
        //         k = scanner.nextInt();

        //         matriks.OBE(3, baris - 1, k, baris2 - 1);
        //     }
        //     matriks.printMatrix();

        //     System.out.print("\nOperasi: ");
        //     operasi = scanner.nextInt();
        // }

        // SPL.SPLGaussJordan(matriks);

        File file = new File("./test/tes_balikangaussjordan.txt");
        Matriks matriks = ReadMatrixFromFile(file);

        Matriks inv = Balikan.BalikanGaussJordan(matriks);
        inv.SaveMatrixToFile("result_bgj.txt");

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
                matrixString += ((j == this.col - 1) && (i < this.row - 1)) ? "\n" : " ";
            }
        }

        FileWriter writer = new FileWriter(filename);
        writer.write(matrixString);
        writer.close();
    }

    // 5. Multiply
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

    // 6. Transpose
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

    // 7. OBE
    public void OBE(int operasi, int baris, double k, int baris2) {
        // Operasi = 1 -> Kalikan baris dengan k
        // Operasi = 2 -> Tukar baris dengan baris2
        // Operasi = 3 -> Tambahkan baris dengan k * baris2
        if (operasi == 1) {
            int j;
            for (j = 0; j < this.col; j++) {
                this.matrix[baris][j] *= k;
            }
        } else if (operasi == 2) {
            int j;
            for (j = 0; j < this.col; j++) {
                double temp = this.matrix[baris][j];
                this.matrix[baris][j] = this.matrix[baris2][j];
                this.matrix[baris2][j] = temp;
            }
        } else if (operasi == 3) {
            int j;
            for (j = 0; j < this.col; j++) {
                this.matrix[baris][j] += k * this.matrix[baris2][j];
            }
        }
    }
    // 8. Kofaktor
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

    // 9. Matriks Kofaktor
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

    // 10. Matriks Identitas
    public static Matriks MatriksIdentitas(int n) {
        Matriks m = new Matriks(n, n);
        int i, j;
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                m.matrix[i][j] = (i == j) ? 1 : 0;
            }
        }
        return m;
    }

    // 11. Matriks Nol
    public static Matriks MatriksNol(int n) {
        Matriks m = new Matriks(n, n);
        int i, j;
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                m.matrix[i][j] = 0;
            }
        }
        return m;   
    }

    // 12. Multiply by k
    public void MultiplyByConstant(double k) {
        int i, j;
        for (i = 0; i < this.row; i++) {
            for (j = 0; j < this.col; j++) {
                this.matrix[i][j] *= k;
            }
        }
    }
}
