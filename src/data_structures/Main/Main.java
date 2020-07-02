package data_structures.Main;


import data_structures.List.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>(2);
        list.add(0,1);
        list.add(1,2);
        list.add(2,3);
        list.add(3,4);
        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));
        System.out.println(list.get(3));
    }
}
