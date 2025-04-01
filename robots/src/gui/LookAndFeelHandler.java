package gui;

import javax.swing.*;
import java.awt.*;

public class LookAndFeelHandler {

    public static void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(getRootComponent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Component getRootComponent() {
        return JFrame.getFrames()[0];
    }
}
