package controller.component;

import javax.swing.*;
import java.io.File;

public class FileLoader extends JFileChooser {

    public

    public void loadFile(){
        int DIALOG_CONDITION = this.showDialog(null,"Load");
        if(DIALOG_CONDITION == JFileChooser.APPROVE_OPTION){
            File selectedFile = this.getSelectedFile();
            System.out.println("Selected File: " + selectedFile.getAbsolutePath());
        }
    }

}
