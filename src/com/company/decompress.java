package com.company;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;

import static com.company.Main.categoryTable;

public class decompress {

    public static String decode(Hashtable<Integer, Map<Integer, String>> categoryTable, String data) {
//string.split("\\r?\\n");
//        System.out.println(Arrays.toString(data.split("\\r?\\n")));
        data = data.replaceAll(" ","");
        String huffman = data.split("\\r?\\n")[0].replace("{","").replace("}","");
        String [] huffmanTable = huffman.split(",");
        String [] binary = data.split("\\r?\\n")[1].split(",");
        System.out.println(Arrays.toString(huffmanTable));
        System.out.println(Arrays.toString(binary));

        Hashtable<String, String> code_table_hash = new Hashtable<>();

        for (String code : huffmanTable){
            code_table_hash.put(code.split("=")[1],code.split("=")[0]);
        }

        String decode = "";
        boolean get_from_category = false;
        int level = 0;
        for (int i = 0; i < binary.length; i++) {
            if(i == binary.length - 1) {
                decode += code_table_hash.get(binary[i]);
                break;
            }
            if(get_from_category){
                decode += search_category_table(binary[i],level) + ",";
                get_from_category = false;
            }else {
                int zeros = getZero(code_table_hash.get(binary[i]));
                level = getValue(code_table_hash.get(binary[i]));
                for (int j = 0; j < zeros ; j++) {
                    decode += "0,";
                }
                get_from_category = true;
            }
        }
        System.out.println(code_table_hash);
        return decode;
    }

    private static int getValue(String code) {
        if (code.equals("EOB"))
            return 0;
        System.out.println(" getValue " + code.substring(code.indexOf('/')));

        return Integer.valueOf( code.substring(code.indexOf('/') + 1) ) ;
    }
    private static int getZero(String code) {
        if (code.equals("EOB"))
            return 0;
        System.out.println(" zero " +code.substring(0,code.indexOf('/')));
        return Integer.valueOf(code.substring(0,code.indexOf('/')));
    }

    private static int search_category_table(String binary,int level){

        return categoryTable.get(level).entrySet()
                .stream()
                .filter(e-> e.getValue().equalsIgnoreCase(binary))
                .findFirst() // process the Stream until the first match is found
                .map(Map.Entry::getKey) // return the key of the matching entry if found
                .orElse(-1); // return -1 if no match was found
    }
}
