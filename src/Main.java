import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;
import controller.MenuBar;
import controller.action.GLListener;
import model.DataSet;
import view.MainFrame;

import javax.swing.*;

public class Main {
    /**
     * Assign object here
     * ----------------------------------------------------------------------------------------------------------------
     */

    private static final MainFrame window = new MainFrame("2D Data Visualization",800,600,10);
    private static final MenuBar menuBar = new MenuBar(window);

    /**
     * implement component by function
     * ----------------------------------------------------------------------------------------------------------------
     */
    private static void initWindow(){
        window.setLayout(null);
        window.setVisible(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    private static void menuView(){
        menuBar.addMainMenu("File");
        menuBar.addMainMenu("View");
        menuBar.addMainMenu("Help");
        menuBar.addMenuItem("File","Load File");
        menuBar.addMenuItem("File","Exit");
        menuBar.addMenuItem("View","Pie Chart");
        menuBar.addMenuItem("View","Bar Chart");
        menuBar.addMenuItem("View","Column Chart");
        menuBar.addMenuItem("View","Scatter Plot");
        menuBar.addMenuItem("Help","About 2D Data Visualization");
        menuBar.addMenuItem("Help","Contact Us");

        window.setJMenuBar(menuBar);
    }

    public static void main(String[] args) throws InterruptedException {
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        GLCanvas glCanvas = new GLCanvas(capabilities);
        glCanvas.addGLEventListener(new GLListener(menuBar.getDataSet(),menuBar.getViewSelected()));
        glCanvas.setSize(window.getScreenWidth(),window.getScreenHight());
        window.getContentPane().add(glCanvas);
        FPSAnimator fpsAnimator = new FPSAnimator(glCanvas,1);
        fpsAnimator.start();
        menuView();
        initWindow();


    }


}
