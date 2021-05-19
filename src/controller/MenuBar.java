package controller;

import controller.action.MenuItemListener;
import model.DataSet;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class MenuBar extends JMenuBar{

    //handle all component and model that it required
    private HashMap<String,JMenu> menus = new HashMap<>();
    private HashMap<String,JMenuItem> menuItems = new HashMap<>();
    private DataSet dataSet = new DataSet();
    private StringBuilder viewSelected = new StringBuilder("Pie Chart");
    private MenuItemListener menuItemListener = new MenuItemListener(dataSet,viewSelected);

    public MenuBar(JFrame parent){
    }

    public void addMainMenu(String title){
        this.menus.put(title,new JMenu(title));
        this.add(this.menus.get(title));
    }

    public void addMenuItem(String key, String title){
        this.menuItems.put(title, new JMenuItem(title));
        this.menuItems.get(title).addActionListener(menuItemListener);
        this.menus.get(key).add(this.menuItems.get(title));
    }

    public JMenu getMainMenu(String key){ return this.menus.get(key); }
    public JMenuItem getMenuItem(String key){
        return this.menuItems.get(key);
    }
    public DataSet getDataSet(){return dataSet;}
    public StringBuilder getViewSelected(){ return  viewSelected;}
}
