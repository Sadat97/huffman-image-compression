package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Huff  extends JFrame {
    public JPanel p1;
    public JRadioButton compress;
    public JRadioButton decompress;
    public JButton button1;

    public Huff() {
        add(p1);
        setTitle("Huff Jpeg");
        setSize(500,400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }


}
