package controller.component;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

public class FileLoader extends JFileChooser {

    public FileLoader(){
        this.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if(f.isDirectory()){
                    return true;
                } else {
                    return f.getName().endsWith(".txt") || f.getName().endsWith(".csv");
                }
            }

            @Override
            public String getDescription() {
                return "text file (*.txt) or csv file (*.csv)";
            }
        });
        this.setAcceptAllFileFilterUsed(false);
    }

    public void loadFile(){
        int DIALOG_CONDITION = this.showDialog(null,"Load");
        if(DIALOG_CONDITION == JFileChooser.APPROVE_OPTION){
            File selectedFile = this.getSelectedFile();
            System.out.println("Selected File: " + selectedFile.getAbsolutePath());
        }
    }

}
