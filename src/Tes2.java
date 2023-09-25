import java.util.Arrays;

public class Tes2 {
    public static int[] appendToArray(int[] array, int value) {
        int[] newArray = new int[array.length + 1];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        newArray[array.length] = value;

        return newArray;
    }

    public static void main(String[] args) {
        int[] hello = {};
        hello = appendToArray(hello, 0);
        System.out.println(Arrays.toString(hello));

        
    }
}
