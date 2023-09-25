import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import javax.imageio.ImageIO;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Tes {
    public static void main(String[] args) throws IOException {
        // BufferedImage image15 = new BufferedImage(15, 15, BufferedImage.TYPE_BYTE_GRAY);
        // BufferedImage image12 = new BufferedImage(12, 12, BufferedImage.TYPE_BYTE_GRAY);
        // BufferedImage image13 = new BufferedImage(13, 13, BufferedImage.TYPE_BYTE_GRAY);

        // WritableRaster raster = image15.getRaster();
        // WritableRaster raster12 = image12.getRaster();
        // WritableRaster raster13 = image13.getRaster();
        // double factor = 2.5;
        // double factor12 = 2;
        // double factor13 = 2.3;

        // // for (int i = 0; i < 15; i++) {
        // //     for (int j = 0; j < 15; j++) {
        // //         if (((i / (double) factor) == Math.floor((i / (double) factor))) && ((j / (double) factor) == Math.floor((j / (double) factor)))) {
        // //             raster.setSample(j, i, 0, 0);
        // //         } else {
        // //             // if (i <= 1 || i >= 10 || j <= 1 || j >= 10) {
        // //             //     raster.setSample(j, i, 0, 127);    
        // //             // } else {
        // //             // }
        // //             double diffJ = ((double) j) - (factor * Math.round(j / factor));
        // //             double diffI = ((double) i) - (factor * Math.round(i / factor));

        // //             boolean isJ = diffJ == 0.5 || diffJ == -0.5;
        // //             boolean isI = diffI == 0.5 || diffI == -0.5;
        // //             boolean iPixel = ((i / (double) factor) == Math.floor((i / (double) factor)));
        // //             boolean jPixel = ((j / (double) factor) == Math.floor((j / (double) factor)));

        // //             if (isJ && isI) {
        // //                 raster.setSample(j, i, 0, 127);
        // //             } else if ((isJ && iPixel) || (isI && jPixel)) {
        // //                 raster.setSample(j, i, 0, 127);
        // //             } else {
        // //                 raster.setSample(j, i, 0, 255);
        // //             }

        // //         }
        // //     }
        // // }

        // // for (int i = 0; i < 12; i++) {
        // //     for (int j = 0; j < 12; j++) {
        // //         if ((i / factor12) == Math.floor((i / factor12)) && (j / factor12) == Math.floor((j / factor12))) {
        // //             raster12.setSample(j, i, 0, 0);
        // //         } else {
        // //             raster12.setSample(j, i, 0, 255);
        // //         }
        // //     }
        // // }

        // int[] pixels13 = {};
        // for (int i = 0; i < 6; i++) {
        //     if ((factor13 * i) == Math.floor((factor13 * i))) {
        //         pixels13 = appendToArray(pixels13, (int) (factor13 * i));
        //     } else {
        //         // pixels13 = appendToArray(pixels13, (int) Math.floor((factor13 * i)));
        //         // pixels13 = appendToArray(pixels13, (int) Math.ceil((factor13 * i)));
        //         pixels13 = appendToArray(pixels13, (int) Math.round(factor13 * i));
        //     }
        // }

        // for (int i = 0; i < 13; i++) {
        //     for (int j = 0; j < 13; j++) {
        //         if (inArray(pixels13, i) && inArray(pixels13, j)) {
        //             raster13.setSample(j, i, 0, 0);
        //         } else {
        //             raster13.setSample(j, i, 0, 255);
        //         }
        //     }
        // }

        // try {
        //     File outputFile = new File("./test/15x15.png");
        //     File outputFile12 = new File("./test/12x12.png");
        //     File outputFile13 = new File("./test/13x13.png");

        //     ImageIO.write(image15, "png", outputFile);
        //     ImageIO.write(image12, "png", outputFile12);
        //     ImageIO.write(image13, "png", outputFile13);
        // } catch (IOException e) {
        //     System.out.println(e);
        // }

        // generateResizedImage(6, 4, "resizePixelMapping");

        // File file = new File("./test/resizePixelMapping.png");
        // BufferedImage image = ImageIO.read(file);
        // WritableRaster raster = image.getRaster();

        // System.out.println(raster.getSample(0, 0, 0));
        // System.out.println(raster.getSampleDouble(1, 0, 0));
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

    public static void generateResizedImage(int originalSize, double factor, String outputFileName) {
        int newSize = (int) Math.floor(originalSize * factor);
        BufferedImage image = new BufferedImage(newSize, newSize, BufferedImage.TYPE_BYTE_GRAY);

        WritableRaster raster = image.getRaster();

        int[] pixels = {};
        for (int i = 0; i < originalSize; i++) {
            boolean isInteger = (factor * i) == Math.floor((factor * i));
            if (isInteger) {
                pixels = appendToArray(pixels, (int) (factor * i));
            } else {
                pixels = appendToArray(pixels, (int) (Math.round((factor * i))));
            }
        }

        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                if (inArray(pixels, i) && inArray(pixels, j)) {
                    raster.setSample(j, i, 0, 0);
                } else {
                    raster.setSample(j, i, 0, 255);
                }
            }
        }

        try {
            File outputFile = new File("./test/" + outputFileName + ".png");
            ImageIO.write(image, "png", outputFile);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
