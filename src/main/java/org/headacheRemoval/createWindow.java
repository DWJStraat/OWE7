package org.headacheRemoval;

import javax.swing.*;

/**
 * A class that creates a window with a given title, width and height.
 * It also allows for the addition of components to the window.
 * It also allows for the display of error messages.
 */
public class createWindow {
    public JFrame frame;

    /**
     * Creates a window with a given title, width and height.
     * @param title The title of the window
     * @param width The width of the window
     * @param height The height of the window
     */
    public createWindow(String title, int width, int height) {
        build(title, width, height);
    }

    /**
     * Creates a window with a given title, width and height.
     * @param title The title of the window
     * @param width The width of the window
     * @param height The height of the window
     */
    public void build(String title, int width, int height) {
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setLayout(null);
    }

    /**
     * Displays the window.
     */
    public void show() {
        frame.setVisible(true);
    }

    /**
     * Adds a component to the window at a given position with a given width and height.
     * @param component The component to be added
     * @param position_x The x position of the component
     * @param position_y The y position of the component
     * @param width The width of the component
     * @param height The height of the component
     */
    public void add(JComponent component, int position_x, int position_y, int width, int height) {
        frame.add(component);
        component.setBounds(position_x, position_y, width, height);
    }

    /**
     * Displays an error message.
     * @param message The message to be displayed
     */
    public void error(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void close() {
        frame.dispose();
    }

}
