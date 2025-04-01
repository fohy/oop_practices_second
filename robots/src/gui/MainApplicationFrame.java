package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private WindowGeometry windowGeometry;

    public MainApplicationFrame() {
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width - inset * 2,
                screenSize.height - inset * 2);

        setContentPane(desktopPane);

        windowGeometry = new WindowGeometry();

        LogWindow logWindow = WindowCreator.createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = WindowCreator.createGameWindow();
        addWindow(gameWindow);

        setJMenuBar(MenuBarCreator.createMenuBar(this));

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                windowGeometry.saveState(gameWindow);
                windowGeometry.saveState(logWindow);
                ExitHandler.confirmExit(MainApplicationFrame.this);
            }
        });
    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);

        windowGeometry.restoreState(frame);
    }

    public void setLookAndFeel(String className) {
        LookAndFeelHandler.setLookAndFeel(className);
    }

    public void confirmExit() {
        ExitHandler.confirmExit(this);
    }
}

