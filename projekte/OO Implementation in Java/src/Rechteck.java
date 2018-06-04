public class Rechteck{
    private int länge;
    private int breite;
    private int umfang;

    public Rechteck(){
        System.out.println("Constructor: Rechteck()");
    }

    public Rechteck(int länge){
        System.out.println("Constructor: Rechteck(int länge)");
    }

    public Rechteck(int länge,int breite){
        System.out.println("Constructor: Rechteck(int länge, int breite)");
    }

    public int getLänge(){
        return this.länge;
    }

    public int getBreite(){
        return this.breite;
    }

    public void setLänge(int länge){
        this.länge = länge;
    }

    public void setBreite(int breite){
        this.breite = breite;
    }}
