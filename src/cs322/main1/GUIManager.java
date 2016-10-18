package cs322.main1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by RangeWING on 2016-10-18.
 */
public class GUIManager {
    private JFrame frame;
    private TextField textField;
    private StringBuilder inputString;
    public GUIManager(){
        init();
    }
    public void init(){
        frame = new JFrame("CS322 16F Project 1 by 20150717");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setSize(400, 80);
        textField = new TextField(50);
        frame.add(textField);
        frame.setVisible(true);
        inputString = new StringBuilder();
        textField.setEditable(false);
    }
    public void addKeyListener(Hangeul hangeul, HMealy hMealy){
        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if(c == '\b') c = '<';
                if(c == '\n') inputString = new StringBuilder();
                if(Character.isAlphabetic(c) || c == '<') {
                    inputString.append(c);
                }
                String out = hangeul.getOutput(hMealy, inputString.toString());
                if(out != null)
                    textField.setText(hangeul.getOutput(hMealy, inputString.toString()));
                else textField.setText("Not valid input. Press enter to clear");
                System.out.println(inputString);
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }
}
