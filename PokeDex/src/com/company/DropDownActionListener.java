package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class DropDownActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent ex) {
        JComboBox j = (JComboBox) ex.getSource();
        BufferedImage image = null;
        if (j.getSelectedItem() != null) {
            try {
                HashMap<Object, Object> j1 = (HashMap<Object, Object>) Main.object.get("sprites");
                HashMap<String, HashMap<String, HashMap<String, String>>> n = (HashMap<String, HashMap<String, HashMap<String, String>>>) j1.get("versions");
                HashMap<String, HashMap<String, String>> c = n.get(j.getSelectedItem());
                HashMap<String, String> x = c.get(c.keySet().toArray()[0]);
                URL url = new URL(x.get("front_default"));
                image = ImageIO.read(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Main.image.setIcon(new ImageIcon(image.getScaledInstance(250,250,Image.SCALE_DEFAULT)));
        }
    }
}
