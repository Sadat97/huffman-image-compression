package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class Main {
    static Hashtable<Integer, Map<Integer, String>> categoryTable = new Hashtable<>();

    public static void main(String[] args) {
        // write your code here
//        String [] data = "-2,0,0,2,0,0,3,2,0,1,0,0,-2,0,-1,0,0,1,0,0,-1,000000000".split(",");
//        for (String number : data)
//          System.out.println(number);
        create_category_table();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Huff gui = new Huff();
                gui.setVisible(true);
                gui.button1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        JFileChooser fc = new JFileChooser();
                        fc.showOpenDialog(gui.p1);
                        File file = fc.getSelectedFile();
                        String text = null;
                        try {
                            text = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String data;
                        if (gui.compress.isSelected()) {
                            data = compress.main(categoryTable,text.split(","));
                            try {
                                writeToFile(data,"/home/sadat/Desktop/huffman-jpeg/src/com/company/compress.txt");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
//                            text = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
//                            text = "{0/2=11, 1/1=010, 2/1=10, 2/2=00, EOB=011}\n" +
//                                    "11,01,00,10,00,11,11,10,010,1,00,01,010,0,10,1,10,0,011";
                            String decompressed_data = (decompress.decode(categoryTable,text));
                            try {
                                writeToFile(decompressed_data,"/home/sadat/Desktop/huffman-jpeg/src/com/company/decompress.txt");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

//                        System.out.println("Working on it");
//                            data = decoder.decode(text);
//                    writeToFile(data,"/home/sadat/Desktop/decompressed_data.txt");
                        }

                    }
                });
            }
        });


    }


    private static void create_category_table() {
        int cnt = 0;
        for (int i = 1, j = 1; i <= 5; i++) {
            Map<Integer, String> category = new HashMap<Integer, String>();
            for (int x = ((int) StrictMath.pow(2, i)) - 1; x >= j; x--) {
                var binary = Integer.toBinaryString(cnt);
                while (binary.length() < i) {
                    binary = "0" + binary;
                }
                category.put(-1 * x, binary);
                cnt++;
            }
            for (; j < StrictMath.pow(2, i); j++) {
                String binary = Integer.toBinaryString(cnt);
                while (binary.length() < i)
                    binary = "0" + binary;
                category.put(j, binary);
                cnt++;
            }
            categoryTable.put(i, category);
            cnt = 0;
        }

    }

    public static void writeToFile(String data, String fileName) throws  IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(data);
        writer.close();
    }
}
