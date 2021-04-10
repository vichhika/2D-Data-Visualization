package controller.action;

import controller.component.FileLoader;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuItemListener implements ActionListener {

    // add Model in here

    // add more component here
    FileLoader fileLoader = new FileLoader();

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        switch (e.getActionCommand()){
            case "Load File":
                fileLoader.loadFile();
                break;
            case "Exit":
                System.exit(0);
                break;
        }
    }
}
