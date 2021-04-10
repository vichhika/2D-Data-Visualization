import controller.component.MenuBar;

import javax.swing.*;

public class Main {
    /**
     * Assign object here
     * ----------------------------------------------------------------------------------------------------------------
     */
    private static final JFrame window = new JFrame("2D Data Visualization");
    private static final MenuBar menuBar = new MenuBar(window);

    /**
     * implement component by function
     * ----------------------------------------------------------------------------------------------------------------
     */
    private static void initWindow(){
        window.setSize(800,600);
        window.setLayout(null);
        window.setVisible(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    private static void menuView(){
        menuBar.addMainMenu("File");
        menuBar.addMainMenu("Help");
        menuBar.addMenuItem("File","Load File");
        menuBar.addMenuItem("File","Exit");
        menuBar.addMenuItem("Help","About 2D Data Visualization");
        menuBar.addMenuItem("Help","Contact Us");

        window.setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        initWindow();
        menuView();
    }
}
