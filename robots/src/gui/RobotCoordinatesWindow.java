package gui;

import model.RobotModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RobotCoordinatesWindow extends JInternalFrame {
    private final RobotModel model;
    private final JLabel coordinatesLabel;

    public RobotCoordinatesWindow(RobotModel model) {
        super("Координаты робота", true, true, true, true);
        this.model = model;
        this.coordinatesLabel = new JLabel("X: 0, Y: 0");

        setLayout(new BorderLayout());
        add(coordinatesLabel, BorderLayout.CENTER);
        setSize(200, 100);

        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCoordinates();
            }
        });
        timer.start();
    }

    private void updateCoordinates() {
        double x = model.getRobotPositionX();
        double y = model.getRobotPositionY();
        coordinatesLabel.setText(String.format("X: %.2f, Y: %.2f", x, y));
    }
}
