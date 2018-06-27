package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Integer> intList = new ArrayList<>();
        for(int i = 0;i<20;i++){
            intList.add(i * 5);
        }

        System.out.println(intList.toString());

        int coolNumber = intList.stream()
                .map(zahl -> zahl+"petersilie")
                .map(string ->string.replace("eter","silie"))
                .filter(string -> string.contains("0p"))
                .distinct()
                .reduce((str1,str2) -> str1.substring(0,7)+str2.substring(7))
                .hashCode();

        System.out.println("the great lottery number today is: "+coolNumber);

    }
}
