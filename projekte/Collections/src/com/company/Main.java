package com.company;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        HashMap<Integer,Integer> myHashMap = new HashMap<>();

        for(int i = 0;i<10000;i++){
            myHashMap.put(Integer.hashCode(i),i);
        }

        System.out.println(myHashMap.get(12));
    }
}
