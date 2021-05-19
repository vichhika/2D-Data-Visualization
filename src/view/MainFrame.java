package view;

import javax.swing.*;

public class MainFrame extends JFrame {

    public int screenWidth;
    public int screenHight;
    public int unit;

    public MainFrame(String title,int width,int hight,int unit){
        this.screenHight = hight;
        this.screenWidth = width;
        this.unit = unit;
        this.setTitle(title);
        this.setSize(screenWidth,screenHight);
        this.setResizable(false);
    }

    public int getScreenWidth(){
        return  this.getWidth();
    }

    public int getScreenHight(){
        return  this.getHeight();
    }
}
