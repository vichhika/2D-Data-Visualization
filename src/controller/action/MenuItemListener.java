package controller.action;

import controller.FileLoader;
import model.DataSet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MenuItemListener implements ActionListener {


    // add more component here
    FileLoader fileLoader = new FileLoader();
    DataSet dataSet;
    StringBuilder viewSelected;

    public MenuItemListener(DataSet dataSet,StringBuilder viewSelected){
        this.dataSet = dataSet;
        this.viewSelected = viewSelected;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "Load File":
                dataSet.clear();
                dataSet.setData(fileLoader.loadFile());
                break;
            case "Pie Chart":
                viewSelected.delete(0,viewSelected.length());
                viewSelected.append(e.getActionCommand());
                break;
            case "Bar Chart":
                viewSelected.delete(0,viewSelected.length());
                viewSelected.append(e.getActionCommand());
                break;
            case "Column Chart":
                viewSelected.delete(0,viewSelected.length());
                viewSelected.append(e.getActionCommand());
                break;
            case "Scatter Plot":
                viewSelected.delete(0,viewSelected.length());
                viewSelected.append(e.getActionCommand());
                break;
            case "Contact Us":
                String url_open_profile ="https://github.com/vichhika";
                try {
                    java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_open_profile));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                break;
            case "About 2D Data Visualization":
                String url_open_help ="https://github.com/vichhika/2D-Data-Visualization/blob/main/README.md";
                try {
                    java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_open_help));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                break;
            case "Exit":
                System.exit(0);
                break;
        }
    }
}
