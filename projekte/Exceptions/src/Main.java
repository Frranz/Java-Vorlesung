

public class Main {

    public static void main(String[] args) {
        int zahl = 7;
        int zahl2 = 0;
        Calculator calc = new Calculator();
        try {
            System.out.println(calc.divide(zahl, zahl2));
        }catch(ZeroDivideException e){
            System.out.print("zahl2 cant be zero dude wtfs");
        }catch(Exception e){
            System.out.println("just a normal exception man dont worry too much");
        }
    }
}

class ZeroDivideException extends Exception{
    public static void main(){

    }
}

class Calculator{
    public Calculator(){
        System.out.println("welcome to the calculator");
    }

    public static int divide (int zahl, int zahl2)throws ZeroDivideException{
        if(zahl2==0){
            throw new ZeroDivideException();
        }else{
            return zahl/zahl2;
        }
    }
}
