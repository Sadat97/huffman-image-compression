package com.company;

import java.util.*;

public class compress {

    public static String main(Hashtable<Integer, Map<Integer, String>> categoryTable, String [] data){
        ArrayList<String> formulate = formulate(data);
        ArrayList<String> formulate_with_level = formulate_with_level(formulate);
        Map<String, Integer> code_and_prob = getProb(formulate_with_level);
        HashMap<String, String> huffman_table =  Huffman.buildCode(code_and_prob);
        String compressed = huffman_table + "\n";
        for (int i = 0; i < formulate_with_level.size(); i++) {
            String code = formulate_with_level.get(i);
            compressed += huffman_table.get(code);
            if (code.equals("EOB"))
                continue;
            String integer = formulate.get(i);
            int level = getValue(code);
            int value = getValue(integer);
            String binary =  (categoryTable.get(level).get(value));
            compressed +=',' + binary +',';
        }
        return compressed;
    }

    private static int getValue(String code) {
        if (code.equals("EOB"))
            return 0;

        return Integer.valueOf(code.substring(code.indexOf('/')+1));
    }


    private static ArrayList<String> formulate(String[] data){
        ArrayList<String> formulated_data = new ArrayList<>();
        int zero_counter = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i].charAt(0) == '0'){
                zero_counter += 1;
                if (i == data.length -1)
                    formulated_data.add("EOB");
            }else{
                formulated_data.add(zero_counter + "/" + data[i]);
                zero_counter = 0;
            }
        }
        System.out.println("form " + formulated_data);
        return formulated_data;
    }

    private static ArrayList<String> formulate_with_level(ArrayList<String> data){
        ArrayList<String> f_w_l = new ArrayList<>();
        String temp = "";
        int level;
        for (int i = 0; i < data.size() - 1; i++) {
            temp = data.get(i);
            temp = temp.replace("-", "");
            level = log2(Integer.valueOf(temp.substring(temp.indexOf('/')+1)));
            f_w_l.add(temp.substring(0, temp.indexOf('/')+1)+level);
        }
        f_w_l.add("EOB");

        return f_w_l;
    }

    private static Map<String, Integer> getProb (ArrayList<String> data){
        ArrayList <Double> prob = new ArrayList<>();
        Map<String, Integer> map = new HashMap<String, Integer>();

        for(String huffmancode: data) {
            // if word is not in the map
            if(!map.containsKey(huffmancode))
                map.put(huffmancode, 0);
            map.put(huffmancode, map.get(huffmancode) + 1);
        }

//        System.out.println("map size " + map.size());
//
//        for(String huffcode: map.keySet())
//            System.out.println(word + " occurs " + map.get(word) + " times");
        return map;
    }

    private static int log2(int value) {
        return Integer.SIZE-Integer.numberOfLeadingZeros(value);
    }





}



