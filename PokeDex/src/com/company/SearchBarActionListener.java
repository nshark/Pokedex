package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchBarActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        Main.panel.removeAll();
        for (JButton b : Main.buttons){
            if (b.getText().contains(Main.jta.getText())){
                Main.panel.add(b);
            }
        }
        Main.frame.pack();
    }
}
