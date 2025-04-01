package gui;

import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class WindowGeometry extends AbstractWindowGeometry {
    private static final String STATE_FILE = "window_state.properties";
    private final Map<String, WindowState> windowStates = new HashMap<>();

    @Override
    protected void saveWindowStateToFile(JInternalFrame frame) {
        WindowState state = new WindowState(
                frame.getX(),
                frame.getY(),
                frame.getWidth(),
                frame.getHeight(),
                frame.isMaximum()
        );
        windowStates.put(frame.getTitle(), state);

        saveAllStatesToFile();
    }

    private void saveAllStatesToFile() {
        Properties properties = new Properties();

        for (Map.Entry<String, WindowState> entry : windowStates.entrySet()) {
            String prefix = entry.getKey() + ".";
            WindowState state = entry.getValue();

            properties.setProperty(prefix + "lastX", String.valueOf(state.lastX));
            properties.setProperty(prefix + "lastY", String.valueOf(state.lastY));
            properties.setProperty(prefix + "lastWidth", String.valueOf(state.lastWidth));
            properties.setProperty(prefix + "lastHeight", String.valueOf(state.lastHeight));
            properties.setProperty(prefix + "wasMaximized", String.valueOf(state.wasMaximized));
        }

        try (FileOutputStream fileOut = new FileOutputStream(STATE_FILE)) {
            properties.store(fileOut, "Window states");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void restoreState(JInternalFrame frame) {
        loadAllStatesFromFile();

        WindowState state = windowStates.get(frame.getTitle());
        if (state != null) {
            frame.setBounds(state.lastX, state.lastY, state.lastWidth, state.lastHeight);
            if (state.wasMaximized) {
                try {
                    frame.setMaximum(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadAllStatesFromFile() {
        Properties properties = new Properties();
        try (FileInputStream fileIn = new FileInputStream(STATE_FILE)) {
            properties.load(fileIn);

            for (String key : properties.stringPropertyNames()) {
                String[] parts = key.split("\\.");
                if (parts.length == 2) {
                    String windowTitle = parts[0];

                    if (!windowStates.containsKey(windowTitle)) {
                        int lastX = Integer.parseInt(properties.getProperty(windowTitle + ".lastX", "-1"));
                        int lastY = Integer.parseInt(properties.getProperty(windowTitle + ".lastY", "-1"));
                        int lastWidth = Integer.parseInt(properties.getProperty(windowTitle + ".lastWidth", "-1"));
                        int lastHeight = Integer.parseInt(properties.getProperty(windowTitle + ".lastHeight", "-1"));
                        boolean wasMaximized = Boolean.parseBoolean(properties.getProperty(windowTitle + ".wasMaximized", "false"));

                        windowStates.put(windowTitle, new WindowState(lastX, lastY, lastWidth, lastHeight, wasMaximized));
                    }
                }
            }
        } catch (IOException e) {
        }
    }

    private static class WindowState {
        final int lastX;
        final int lastY;
        final int lastWidth;
        final int lastHeight;
        final boolean wasMaximized;

        WindowState(int lastX, int lastY, int lastWidth, int lastHeight, boolean wasMaximized) {
            this.lastX = lastX;
            this.lastY = lastY;
            this.lastWidth = lastWidth;
            this.lastHeight = lastHeight;
            this.wasMaximized = wasMaximized;
        }
    }
}