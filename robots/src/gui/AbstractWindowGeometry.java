package gui;

import javax.swing.*;

public abstract class AbstractWindowGeometry implements WindowStateSaver {
    @Override
    public void saveState(JInternalFrame frame) {
        saveWindowStateToFile(frame);
    }

    protected abstract void saveWindowStateToFile(JInternalFrame frame);

    @Override
    public abstract void restoreState(JInternalFrame frame);
}