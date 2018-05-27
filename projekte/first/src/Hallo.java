import java.util.Arrays;
import java.util.Random;

public class Hallo {

    private static Random rand;

    public static void main(String[] args){
        int[] array = new int[90];
        rand = new Random();
        initArray(array);
        System.out.print(Arrays.toString(array));
        System.out.println(" ");
        bubbleSort(array);
        System.out.print(Arrays.toString(array));

    }

    private static void initArray(int[] array){
        for(int i=0;i<array.length;i++){
            array[i] = rand.nextInt(5000);
        }
    }

    private static void bubbleSort(int[] array){
        int h;
        for(int i = 0;i<array.length-1;i++){
            for(int j = 0;j<array.length-1;j++){
                if(array[j]>array[j+1]){
                    h = array[j];
                    array[j] = array[j+1];
                    array[j+1] = h;
                }
            }
        }
    }

    private static int first(int[] array){
        return array[0];
    }
    private static int second(int[] array){
        return array[1];
    }
}
