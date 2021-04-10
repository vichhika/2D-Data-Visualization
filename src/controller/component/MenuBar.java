package controller.component;

import controller.action.MenuItemListener;

import javax.swing.*;
import java.util.HashMap;

public class MenuBar extends JMenuBar{

    private HashMap<String,JMenu> menus = new HashMap<>();
    private HashMap<String,JMenuItem> menuItems = new HashMap<>();
    private MenuItemListener menuItemListener = new MenuItemListener();

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

}
