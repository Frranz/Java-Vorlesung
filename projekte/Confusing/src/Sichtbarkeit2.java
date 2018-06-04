public class Sichtbarkeit2 extends Sichtbarkeit{
    private int fünf = 5;
    private int vier = 4;

    public Sichtbarkeit2(){
        int vier = 5;
        int fünf = 4;
        printMyVier();
    }

    public void printMyVier(){
        System.out.printf("Sichbarkeit2, printMyVier1: %d\n", fünf + this.fünf);
        System.out.printf("Sichbarkeit2, printMyVier2: %d\n",this.vier + vier);
    }
}


