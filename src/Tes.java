import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Tes {
    public static void main(String[] args) {
        try {
            File file = new File("./src/tes.txt");
            Scanner scan = new Scanner(file);

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
    
            System.out.printf("%d, %d", rows, cols);

            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
