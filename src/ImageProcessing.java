import java.io.IOException;
import java.io.File;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Random;

import javax.imageio.ImageIO;

public class ImageProcessing {
    public static void main(String[] args) throws IOException {
        Random random = new Random();
        try {
            // BufferedImage image = new BufferedImage(40, 40, BufferedImage.TYPE_BYTE_GRAY);
            // WritableRaster raster = image.getRaster();
            // for (int i = 0; i < 40; i++) {
            //     for (int j = 0; j < 40; j++) {
            //         raster.setSample(j, i, 0, random.nextInt(255));
            //     }
            // }

            // File out = new File("./image.png");
            // ImageIO.write(image, "png", out);
            // Matriks ori = imageToMatrix(image);
            // ori.SaveMatrixToFile("./originalMatrix.txt");

            // BufferedImage res = resizeImage(image, 2);
            // File out4 = new File("./imageResized.png");
            // ImageIO.write(res, "png", out4);
            // Matriks resMat = imageToMatrix(res);
            // resMat.SaveMatrixToFile("./resizedMatrix.txt");
            
            File file = new File("./input.jpg");
            BufferedImage img = ImageIO.read(file);

            img = convertToGrayscale(img);
            File grayOut = new File("./grayscale.png");
            ImageIO.write(img, "png", grayOut);

            BufferedImage resizedImage = resizeImage(img, 2.3);
            File outputFile = new File ("./resizedImage.png");
            ImageIO.write(resizedImage, "png", outputFile);

        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public static Matriks MatriksD() {
        Matriks MatriksD = Matriks.MatriksNol(16);
        int i;
        for (i = 0; i < 16; i++) {
            int x = ((i % 4 == 0) || (i % 4 == 2)) ? 0 : 1;
            int y = ((i % 4 == 0) || (i % 4 == 1)) ? 0 : 1;

            if (i >= 12) {
                MatriksD.matrix[i][(4 * (y + 1) + (x + 1))] = -0.25;
                MatriksD.matrix[i][(4 * (y) + (x + 1))] = -0.25;
                MatriksD.matrix[i][(4 * (y + 1) + (x))] = -0.25;
                MatriksD.matrix[i][(4 * (y + 2) + (x + 2))] = 0.25;
            } else if (i >= 8) {
                MatriksD.matrix[i][(4 * (y + 2) + (x + 1))] = 0.5;
                MatriksD.matrix[i][(4 * (y) + (x + 1))] = -0.5;
            } else if (i >= 4) {
                MatriksD.matrix[i][(4 * (y + 1) + (x + 2))] = 0.5;
                MatriksD.matrix[i][(4 * (y + 1) + (x))] = -0.5;
            } else {
                MatriksD.matrix[i][(4 * (y + 1) + (x + 1))] = 1;
            }
        }
        return MatriksD;
    }

    public static Matriks MatriksI(Matriks mat, int x, int y) {
        Matriks matI = new Matriks(16, 1);

        int matIRow = 0;
        for (int y2 = (y - 1); y2 <= (y + 2); y2++) {
            for (int x2 = (x - 1); x2 <= (x + 2); x2++) {
                int coorX = (x2 < 0) ? 0 : (x2 >= mat.col) ? (mat.col - 1) : x2;
                int coorY = (y2 < 0) ? 0 : (y2 >= mat.row) ? (mat.row - 1) : y2;

                matI.matrix[matIRow][0] = mat.matrix[coorY][coorX];
                matIRow += 1;
            }
        }

        return matI;
    }

    public static BufferedImage convertToGrayscale(BufferedImage img) {
        int width, height;
        width = img.getWidth();
        height = img.getHeight();

        BufferedImage newImage = new BufferedImage(width, height, BufferedImage
        .TYPE_BYTE_GRAY);
        WritableRaster raster = newImage.getRaster();

        int i, j;
        for (i = 0; i < height; i++) {
            for (j = 0; j < width; j++) {
                int pixel = img.getRGB(j, i);
                // int a = (pixel >> 24) & 0xFF;
                int r = (pixel >> 16) & 0xFF;
                int g = (pixel >> 8) & 0xFF;
                int b = pixel & 0xFF;

                int avg = (r + g + b) / 3;

                // pixel = (a << 24) | (avg << 16) | (avg << 8) | (avg);

                // img.setRGB(j, i, pixel);
                raster.setSample(j, i, 0, avg);
            }
        }

        return newImage;
    }
    
    public static Matriks imageToMatrix(BufferedImage img) {
        // Convert image to grayscale
        // img = convertToGrayscale(img);

        // Get raster of image to get pixel grayscale values
        WritableRaster raster = img.getRaster();
        
        // Get width and height of image
        int width = img.getWidth();
        int height = img.getHeight();

        // Construct a matrix with same width and height as image
        Matriks imageMatrix = new Matriks(height, width);

        // Fill matrix with grayscale values of img
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                imageMatrix.matrix[y][x] = raster.getSampleDouble(x, y, 0);
            }
        }
        return imageMatrix;
    }
    
    public static Matriks[][] MatriksOfMatriksA(Matriks imageMatriks) {
        int height = imageMatriks.row;
        int width = imageMatriks.col;
        
        Matriks[][] matOfMat = new Matriks[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Matriks XInverse = Balikan.BalikanGaussJordan(BicubicSplineInterpolation.MatriksX());
                Matriks matI = MatriksI(imageMatriks, x, y);
                Matriks matD = MatriksD();

                // Kalikan XInverse dengan Matriks D (a = Xi * D * I)
                Matriks XInvTimesD = Matriks.Multiply(XInverse, matD);
                
                // Kalikan XInvTimesD dengan Matriks I
                matOfMat[y][x] = Matriks.Multiply(XInvTimesD, matI);
            }
        }

        return matOfMat;
    }

    public static boolean inArray(int[] array, int value) {
        boolean found = false;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                found = true;
                break;
            } 
        }
        return found;
    }

    public static int[] appendToArray(int[] array, int value) {
        int[] newArray = new int[array.length + 1];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        newArray[array.length] = value;

        return newArray;
    }

    public static BufferedImage resizeImage(BufferedImage img, double factor) {
        // Convert image to grayscale
        // img = convertToGrayscale(img);

        // Bukan ChatGPT ya kak, ini memang saya cara ngasih commentnya gini :D
        
        // Get width and height of original image
        int originalWidth = img.getWidth();
        int originalHeight = img.getHeight();

        // Get new width and height of resized iamge
        int newWidth = (int) Math.floor(originalWidth * factor);
        int newHeight = (int) Math.floor(originalHeight * factor);

        // Construct new resized image
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);

        // Get rasters for original image and new image
        WritableRaster originalRaster = img.getRaster();
        WritableRaster newRaster = resizedImage.getRaster();

        // Declare array for pixels in new image that can map to a pixel in the original image
        int[] pixelsX = {};
        int[] pixelsY = {};

        // Find x values in newImage that can map to x values in originalImage
        for (int x = 0; x < originalWidth; x++) {
            boolean isInteger = (factor * x) == Math.floor((factor * x));
            if (isInteger) {
                pixelsX = appendToArray(pixelsX, (int) (factor * x));
            } else {
                pixelsX = appendToArray(pixelsX, (int) (Math.round(factor * x)));
            }
        }

        // Find y values in newImage that can map to x values in originalImage
        for (int y = 0; y < originalHeight; y++) {
            boolean isInteger = (factor * y) == Math.floor((factor * y));
            if (isInteger) {
                pixelsY = appendToArray(pixelsY, (int) (factor * y));
            } else {
                pixelsY = appendToArray(pixelsY, (int) (Math.round(factor * y)));
            }
        }

        // Create a matrix with width and height of original image that stores the MatriksA of each pixel in original image
        Matriks originalImageMatrix = imageToMatrix(img);
        Matriks[][] MatOfMatA = MatriksOfMatriksA(originalImageMatrix);

        // Determine value of each pixel in new image
        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {
                if (inArray(pixelsX, x) && inArray(pixelsY, y)) {
                    // Position of new x and y in original image
                    int oriX = (int) (x / factor);
                    int oriY = (int) (y / factor);

                    // Get value at oriX and oriY
                    double originalPixelValue = originalRaster.getSampleDouble(oriX, oriY, 0);

                    // Set value of oriX and oriY at new image
                    newRaster.setSample(x, y, 0, originalPixelValue);
                } else {
                    // Position of new x and y in original image
                    double oriX = x / factor;
                    double oriY = y / factor;

                    // Floor of oriX and oriY
                    int floorX = (int) Math.floor(oriX);
                    int floorY = (int) Math.floor(oriY);

                    // Fractional values of oriX and oriY
                    double fracX = oriX - (double) floorX;
                    double fracY = oriY - (double) floorY;

                    // Get value of pixel with bicubic spline interpolation
                    Matriks MatA = MatOfMatA[floorY][floorX];
                    double pixelValue = BicubicSplineInterpolation.GetBicubicSplineInterpolation(fracX, fracY, MatA);

                    // Normalize pixel value
                    pixelValue = (pixelValue < 0) ? 0 : (pixelValue > 255) ? 255 : pixelValue;

                    // Set value of pixel in new image
                    newRaster.setSample(x, y, 0, pixelValue);
                }
            }
        }
        return resizedImage;
    }
}
