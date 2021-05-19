package controller;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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

    public String loadFile(){
        String dataRead = "";
        int DIALOG_CONDITION = this.showDialog(null,"Load");
        if(DIALOG_CONDITION == JFileChooser.APPROVE_OPTION){
            File selectedFile = this.getSelectedFile();
            try {
                Scanner readFile = new Scanner(selectedFile);
                while(readFile.hasNextLine()){
                    dataRead += readFile.nextLine()+"\n";
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return dataRead;
    }

}
