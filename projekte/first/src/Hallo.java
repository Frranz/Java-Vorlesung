import java.util.Arrays;
import java.util.Random;

public class Hallo {

    private static Random rand;

    public static void main(String[] args){
        int[] array = new int[90];
        int intToFind = 0;
        rand = new Random();
        initArray(array);
        array[20] = intToFind;
        System.out.print(Arrays.toString(array));
        System.out.println(" ");
        bubbleSort(array);
        System.out.println(Arrays.toString(array));
        int ind = getPositionOfInt(array,intToFind);
        System.out.println(ind);

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

    private static int getPositionOfInt(int[] array,int findMe){
        int counter = 0;
        int arrlen = array.length/4;
        int index = array.length/2;
        while(array[index]!=findMe){
            if(array[index]>findMe){
                index = index - arrlen-1;
            }else {
                index = index + arrlen+1;
            }
            arrlen /= 2;
            counter++;
        }
        System.out.printf("needed %d runs\n",counter);
        return index;
    }
}
