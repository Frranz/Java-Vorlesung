public class Quadrat extends Rechteck {

    public Quadrat(){
        System.out.println("constructor: Quadrat()");
    }

    public Quadrat(int länge){
        super(länge,länge);
        System.out.println("constructor: Quadrat(int länge)");
    }

    @Override
    public void setLänge(int länge){
        super.setLänge(länge);
        super.setBreite(länge);
    }

    @Override
    public void setBreite(int breite){
        super.setLänge(breite);
        super.setBreite(breite);
    }
}
