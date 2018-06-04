public class Sichtbarkeit {
    private int vier = 7;
    private int fünf = 8;

    public Sichtbarkeit(){
        int fünf = 5;
        printMyVier(this.vier,fünf);
        this.vier = 2;
        this.fünf = 1;
        printMyVier(this.vier,fünf);
    }

    private void printMyVier(int fünf, int vier) {
        System.out.printf("Sichbarkeit, printMyVier1: %d\n", fünf + this.fünf);
        System.out.printf("Sichbarkeit, printMyVier2: %d\n",this.vier + vier);
    }
}