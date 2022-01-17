/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package typeFaster.entity;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;
import java.util.Timer;
import javax.swing.JLabel;
import javax.swing.JPanel;
import typeFaster.controler.SQLiteDbManager;

/**
 *
 * @author M_Zahi
 */
public class KeyWord extends JLabel {

    private String text;
    private String nonColoredText;
    private int positionX;
    private int positionY;
    private Timer timer;
    private final String lang;

    public KeyWord(String lang) {
        super();
        this.lang = lang;
        randomBoundsAndFontColor();
        this.setText(getRandomText());
    }

    private void randomBoundsAndFontColor() {
        int i = randInt(1, 8);
        this.setBounds(50 * i, 1, 200, 30);
        this.setFont(new Font("Serif", Font.BOLD, 22));
        i = randInt(1, 5);
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        Color randomColor = new Color(r, g, b);
        this.setForeground(randomColor);

    }

    public void checkIfSubstring(String userText) {
        String str = this.getNonColoredText();
        int lenght = userText.length();
        if (!"".equals(userText)) {
            if (this.nonColoredText.toLowerCase().contains(userText.toLowerCase())) {
                int pos = this.nonColoredText.toLowerCase().indexOf(userText.toLowerCase());
                if (pos == 0) {
                    this.setText("<html><font color =red>" +str.substring(0,lenght) + "</font>" + str.substring(lenght) + "</html> ");
                }
            } else {
                this.setText(this.nonColoredText);
            }
        } else {
            this.setText(this.nonColoredText);
        }
    }

    private String getRandomText() {
        this.nonColoredText = SQLiteDbManager.getWord(lang);
        this.text = this.nonColoredText;
        return this.nonColoredText;
    }

    public String getNonColoredText() {
        return this.nonColoredText;
    }

    public void move(JPanel panel) {
        this.positionX = this.getX();
        this.positionY = this.getY() + 10;
        this.setLocation(positionX, positionY);
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public Timer getTimer() {
        return timer;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public void destroy(JPanel panel) {
        panel.remove(this);
        panel.revalidate();
        panel.repaint();
        this.timer.cancel();
    }

    private int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}