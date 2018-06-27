public class Main {

    public static void main(String[] args) {
        String myString = "0123456789abcdefghijklmnopqrstuvwxyz";
        String newString = myString.replaceAll("\\d","p");
        StringBuffer buffer = new StringBuffer(newString);
        StringBuffer buffer = new StringBuffer(newString);
        StringBuffer buffer = new StringBuffer(newString);
        StringBuffer buffer = new StringBuffer(newString);
        for(int i = 0;i<100;i++){
            buffer.append(i);
        }

        System.out.println(buffer.toString().replaceAll("0","O").substring(3,100).concat("petermeterdetervertreter").toUpperCase().split("deter")[0]);
    }
}
