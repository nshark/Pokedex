package com.company;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main implements ActionListener {
    public static JSONObject obj;
    public static JSONObject object;
    public static Label Stats;
    public static Frame frame;
    public static JLabel image;
    public static JComboBox<String> version;
    public static JTextArea jta;
    public static ArrayList<JButton> buttons = new ArrayList<>();
    public static JPanel panel;
    public static void main(String[] args) {
	// write your code here
        frame = new JFrame("Pokedex");
        JPanel main = new JPanel(new BorderLayout());
        Stats = new Label();
        Stats.setPreferredSize(new Dimension(500,250));
        JPanel statsPanel = new JPanel(new BorderLayout());
        JPanel statsPanel2 = new JPanel(new GridLayout(2,1));
        image = new JLabel();
        image.setPreferredSize(new Dimension(500,100));
        statsPanel2.add(Stats);
        statsPanel2.add(image);
        statsPanel.add(statsPanel2, BorderLayout.CENTER);
        version = new JComboBox<>();
        version.addActionListener(new DropDownActionListener());
        main.add(statsPanel, BorderLayout.CENTER);
        main.add(version, BorderLayout.SOUTH);
        int x = 300;
        int y = 1;
        panel = new JPanel();
        panel.setLayout(new GridLayout(x, y));
        obj = getAPIstatsOf("pokemon?limit=300&offset=0", false);
        ArrayList<Map<String, String>> objResult = (ArrayList<Map<String, String>>) obj.get("results");
        Main m = new Main();
        for (int i = 0; i < x * y; i++) {
            JButton button = new JButton(objResult.get(i).get("name"));
            button.setPreferredSize(new Dimension(100, 100));
            button.addActionListener(m);
            button.setActionCommand(objResult.get(i).get("url"));
            buttons.add(button);
            panel.add(button);
        }
        JPanel com = new JPanel(new BorderLayout());
        JPanel container = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        container.add(panel);
        JScrollPane scrollPane = new JScrollPane(container);
        com.add(scrollPane, BorderLayout.CENTER);
        jta = new JTextArea();
        JButton a = new JButton("ok");
        a.addActionListener(new SearchBarActionListener());
        a.setPreferredSize(new Dimension(15,15));
        JPanel comc = new JPanel(new BorderLayout());
        comc.add(jta, BorderLayout.CENTER);
        comc.add(a, BorderLayout.EAST);
        com.add(comc, BorderLayout.NORTH);
        main.add(com, BorderLayout.EAST);
        frame.add(main);
        frame.setSize(750,250);
        frame.pack();
        frame.setVisible(true);
        frame.requestFocus();

    }
    public static JSONObject getAPIstatsOf(String id, boolean f){
        try {
            URL url;
            if (!f) {
                url = new URL("https://pokeapi.co/api/v2/" + id + "/");
            }
            else{
                url = new URL(id);
            }
            try (InputStream input = url.openStream()) {
                InputStreamReader isr = new InputStreamReader(input);
                BufferedReader reader = new BufferedReader(isr);
                StringBuilder json = new StringBuilder();
                int c;
                while ((c = reader.read()) != -1) {
                    json.append((char) c);
                }
                JSONParser p = new JSONParser();
                return (JSONObject) p.parse(json.toString());
            }
            catch(IOException | ParseException e){
                e.printStackTrace();
            }
        }
        catch (java.net.MalformedURLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        object = getAPIstatsOf(e.getActionCommand(), true);
        HashMap<Object, Object> j = (HashMap<Object, Object>) object.get("sprites");
        HashMap<String, HashMap<String,HashMap<String, String>>> n = (HashMap<String, HashMap<String, HashMap<String, String>>>) j.get("versions");
        version.removeAllItems();
        for (String s : n.keySet()){
            if (n.get(s).get(n.get(s).keySet().toArray()[0]).values().toArray()[0] != null) {
                version.addItem(s);
            }
        }
        ArrayList<HashMap<String,Object>> cv = (ArrayList<HashMap<String, Object>>) object.get("stats");
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        for (HashMap<String, Object> bv : cv){
            HashMap<String,String> bx = (HashMap<String, String>) bv.get("stat");
            sb.append("               " + bx.get("name") + ": " + bv.get("base_stat") + "<br>");
        }
        sb.append("</html>");
        Stats.setText(sb.toString());
    }
}
