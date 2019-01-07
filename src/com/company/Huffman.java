package com.company;

import java.util.*;

class HuffmanNode {
        double data;
        String c;
        HuffmanNode left;
        HuffmanNode right;
    }
    class MyComparator implements Comparator<HuffmanNode> {
        public int compare(HuffmanNode x, HuffmanNode y)
        {
            return (int)(x.data - y.data);
        }
    }

    public class Huffman {
        static HashMap<String,String> huffman_table = new HashMap<>();
        public static void printCode(HuffmanNode root, String s)
        {

            if (root.left == null && root.right == null && root.c.charAt(0) != '+'){
                huffman_table.put(root.c,s);
                return;
            }
            printCode(root.left, s + "1");
            printCode(root.right, s + "0");
        }

        // main function
        public static HashMap<String, String> buildCode(Map<String, Integer> map)
        {
            // creating a priority queue q.
            // makes a min-priority queue(min-heap).
            PriorityQueue<HuffmanNode> q = new PriorityQueue<HuffmanNode>(map.size(), new MyComparator());
            for(String huffcode: map.keySet()){
                HuffmanNode hn = new HuffmanNode();
                hn.c = huffcode;
                hn.data = map.get(huffcode);
                hn.left = null;
                hn.right = null;
                q.add(hn);
            }



            // create a root node
            HuffmanNode root = null;
            while (q.size() > 1) {
                HuffmanNode x = q.peek();
                q.poll();
                HuffmanNode y = q.peek();
                q.poll();
                HuffmanNode f = new HuffmanNode();
                f.data = x.data + y.data;
                f.c = "+";
                f.left = x;
                f.right = y;
                root = f;

                q.add(f);
            }
            printCode(root, "");
            return huffman_table;
        }
    }

