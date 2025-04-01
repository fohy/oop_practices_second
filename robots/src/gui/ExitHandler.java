package gui;

import javax.swing.*;

public class ExitHandler {
    public static void confirmExit(JFrame frame) {
        String[] options = {"Да", "Нет"};
        int response = JOptionPane.showOptionDialog(frame,
                "Вы уверены, что хотите выйти?", "Подтверждение выхода",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                null, options, options[1]);

        if (response == 0) {
            System.exit(0);
        }
    }
}
