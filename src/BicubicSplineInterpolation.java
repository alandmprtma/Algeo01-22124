import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class BicubicSplineInterpolation {
    // Methods
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        File file = new File("./test/tesBSI.txt");
        Matriks input = Matriks.ReadMatrixFromFile(file);

        float x, y;
        System.out.print("Masukkan nilai x: ");
        x = scanner.nextFloat();

        System.out.print("Masukkan nilai y: ");
        y = scanner.nextFloat();

        System.out.printf("%f, %f\n", x, y);

        System.out.println();
        BicubicInterpolation(input, x, y);

        // Matriks MatriksX = MatriksX();
        // MatriksX.SaveMatrixToFile("matriksX.txt");

        // Matriks inversX = Balikan.BalikanGaussJordan(MatriksX);
        // inversX.SaveMatrixToFile("inversX.txt");

        scanner.close();
    }

    public static Matriks BarisMatriksX(int x, int y, boolean turunanX, boolean turunanY) {
        Matriks barisKoef = new Matriks(1, 16);
        if (!turunanX && !turunanY) {
            int i;
            for (i = 0; i < 16; i++) {
                barisKoef.matrix[0][i] = 1;
                
                // Kalikan dengan x
                if (i % 4 != 0) {
                    barisKoef.matrix[0][i] *= x;
                }
    
                // Kalikan dengan y
                if (i > 3) {
                    barisKoef.matrix[0][i] *= y;
                }
            }
        } else if (!turunanY) {
            int i;
            for (i = 0; i < 16; i++) {
                barisKoef.matrix[0][i] = 1;
                
                // Kalikan dengan 0 (kolom 1)
                if (i % 4 == 0) {
                    barisKoef.matrix[0][i] *= 0;
                }

                // Kalikan dengan x (kolom 3 dan 4)
                if ((i % 4 == 2) || (i % 4 == 3)) {
                    barisKoef.matrix[0][i] *= x;

                    // Kolom 3
                    if (i % 4 == 2) {
                        barisKoef.matrix[0][i] *= 2;
                    }
                    // Kolom 4
                    if (i % 4 == 3) {
                        barisKoef.matrix[0][i] *= 3;
                    }
                }
    
                // Kalikan dengan y
                if (i > 3) {
                    barisKoef.matrix[0][i] *= y;
                }
            }   
        } else if (!turunanX) {
            int i;
            for (i = 0; i < 16; i++) {
                barisKoef.matrix[0][i] = 1;
                
                // Kalikan dengan x (kolom 2, 3, 4)
                if (i % 4 != 0) {
                    barisKoef.matrix[0][i] *= x;
                }

                // Kalikan dengan y (baris 3 dan 4)
                if (i > 7) {
                    barisKoef.matrix[0][i] *= y;

                    // Kalikan dengan 2 (baris 3)
                    if (i < 12) {
                        barisKoef.matrix[0][i] *= 2;
                    // Kalikan dengan 3 (baris 4)
                    } else {
                        barisKoef.matrix[0][i] *= 3;
                    }
                }
    
                // Kalikan dengan 0 (baris 1)
                if (i <= 3) {
                    barisKoef.matrix[0][i] *= 0;
                }
            }  
        } else {
            int i;
            for (i = 0; i < 16; i++) {
                barisKoef.matrix[0][i] = 1;

                // Kalikan dengan 0 (baris 1 atau kolom 1)
                if ((i <= 3) || (i % 4 == 0)) {
                    barisKoef.matrix[0][i] *= 0;
                }
                
                // Kalikan dengan x (kolom 3, 4)
                if ((i % 4 == 2) || (i % 4 == 3)) {
                    barisKoef.matrix[0][i] *= x;
                }

                // Kalikan dengan y (baris 3 dan 4)
                if (i > 7) {
                    barisKoef.matrix[0][i] *= y;
                }

                // Kalikan dengan koefisien
                switch (i) {
                    case 6:
                    case 9:
                        barisKoef.matrix[0][i] *= 2;
                        break;
                    case 7:
                    case 13:
                        barisKoef.matrix[0][i] *= 3;
                        break;
                    case 11:
                    case 14:
                        barisKoef.matrix[0][i] *= 6;
                        break;
                    case 10:
                        barisKoef.matrix[0][i] *= 4;
                        break;
                    case 15:
                        barisKoef.matrix[0][i] *= 9;
                        break;
                }
            }   
        }
    return barisKoef;
    }

    public static Matriks MatriksX() {
        Matriks matriksX = new Matriks(16, 16);
        int i, j;
        for (i = 0; i < 16; i++) {
            int x, y;
            x = ((i % 4 == 0) || (i % 4 == 2)) ? 0 : 1;
            y = ((i % 4 == 0) || (i % 4 == 1)) ? 0 : 1;

            boolean turunanX, turunanY;
            turunanX = (((i > 3) && (i < 8)) || (i > 11)) ? true : false;
            turunanY = (i > 7) ? true : false;

            Matriks baris = BarisMatriksX(x, y, turunanX, turunanY);
            for (j = 0; j < 16; j++) {
                matriksX.matrix[i][j] = baris.matrix[0][j];
            }
        }
        return matriksX;
    }

    public static Matriks MatriksA(Matriks input) {
        Matriks MatriksXInvers = Balikan.BalikanGaussJordan(MatriksX());

        int count = 0;
        int i, j;
        Matriks MatriksY = new Matriks(16, 1);
        for (i = 0; i < input.row; i++) {
            for (j = 0; j < input.col; j++) {
                MatriksY.matrix[count][0] = input.matrix[i][j];
                count += 1;
            }
        }

        Matriks MatriksA = Matriks.Multiply(MatriksXInvers, MatriksY);

        return MatriksA;
    }

    public static void BicubicInterpolation(Matriks input, float x, float y) {
        Matriks MatriksA = MatriksA(input);

        int i;
        double result = 0;
        for (i = 0; i < 16; i++) {
            double adder = MatriksA.matrix[i][0];
            
            // Kali x
            if (i % 4 == 1) {
                adder *= x;
            } else if (i % 4 == 2) {
                adder *= x * x;
            } else if (i % 4 == 3) {
                adder *= x * x * x;
            }

            // Kali y
            if (i >= 12) {
                adder *= y * y * y;
            } else if (i >= 8) {
                adder *= y * y;
            } else if (i >= 4) {
                adder *= y;
            }

            result += adder;
        }

        System.out.printf("f(%.2f, %.2f) = %f", x, y, result);
    }
}