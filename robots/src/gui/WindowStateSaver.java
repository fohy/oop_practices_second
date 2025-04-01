package gui;

import javax.swing.*;

public interface WindowStateSaver {
    void saveState(JInternalFrame frame);
    void restoreState(JInternalFrame frame);
}
