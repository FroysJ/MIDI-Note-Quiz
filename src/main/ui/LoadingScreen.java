package ui;

import javax.swing.*;
import java.awt.*;

// image from https://image.freepik.com/free-vector/welcome-goodbye-vector-neon-text-banner_73458-554.jpg
// code from https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git

// represents loading screen
public class LoadingScreen extends JFrame {
    private static final int WIDTH = 805;
    private static final int HEIGHT = 353;
    private JDesktopPane desktop;
    private JInternalFrame loadScreen;

    //EFFECTS: constructs loading screen
    public LoadingScreen() {
        desktop = new JDesktopPane();
        loadScreen = new JInternalFrame("Welcome", false, false, false, false);
        setContentPane(desktop);
        setTitle("Loading Screen");
        setSize(WIDTH, HEIGHT);

        addLoadingPanel();

        loadScreen.pack();
        loadScreen.setVisible(true);
        desktop.add(loadScreen);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        centreOnScreen();
        setVisible(true);

        try {
            Thread.sleep(1250);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        dispose();
    }

    /**
     * Helper to centre loading window on desktop
     */
    //EFFECTS: centres loading screen on desktop
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    //MODIFIES: loadScreen
    //EFFECTS: adds image to loadScreen
    private void addLoadingPanel() {
        ImageIcon background = new ImageIcon("./data/background1.png");
        Image img = background.getImage();
        img.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
        JLabel loadingPanel = new JLabel(background);
        loadScreen.add(loadingPanel);
    }
}
