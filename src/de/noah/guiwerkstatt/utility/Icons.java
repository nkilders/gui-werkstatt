package de.noah.guiwerkstatt.utility;

import javax.swing.*;

import java.awt.*;

import static de.noah.guiwerkstatt.utility.GraphicsUtil.loadIcon;

public interface Icons {
    Icon JAVA = loadIcon("/icons/java@16x16.png");
    Icon BUTTON = loadIcon("/icons/button.png");
    Icon CHECK_BOX = loadIcon("/icons/checkBox.png");
    Icon FRAME = loadIcon("/icons/frame.png");
    Icon LABEL = loadIcon("/icons/label.png");
    Icon PANEL = loadIcon("/icons/panel.png");
    Icon PASSWORD_FIELD = loadIcon("/icons/passwordField.png");
    Icon PROGRESS_BAR = loadIcon("/icons/progressBar.png");
    Icon RADIO_BUTTON = loadIcon("/icons/radioButton.png");
    Icon SCROLL_BAR = loadIcon("/icons/scrollBar.png");
    Icon SCROLL_PANE = loadIcon("/icons/scrollPane.png");
    Icon SEPARATOR = loadIcon("/icons/separator.png");
    Icon SLIDER = loadIcon("/icons/slider.png");
    Icon SPINNER = loadIcon("/icons/spinner.png");
    Icon SPLIT_PANE = loadIcon("/icons/splitPane.png");
    Icon TABBED_PANE = loadIcon("/icons/tabbedPane.png");
    Icon TEXT_AREA = loadIcon("/icons/textArea.png");
    Icon TEXT_FIELD = loadIcon("/icons/textField.png");
    Icon TEXT_PANE = loadIcon("/icons/textPane.png");
    Icon TREE = loadIcon("/icons/tree.png");

    static Icon getFromClass(Class<? extends Component> clazz) {
        if (clazz == JButton.class) {
            return BUTTON;
        } else if (clazz == JCheckBox.class) {
            return CHECK_BOX;
        } else if (clazz == JFrame.class) {
            return FRAME;
        } else if (clazz == JLabel.class) {
            return LABEL;
        } else if (clazz == JPanel.class) {
            return PANEL;
        } else if (clazz == JPasswordField.class) {
            return PASSWORD_FIELD;
        } else if (clazz == JProgressBar.class) {
            return PROGRESS_BAR;
        } else if (clazz == JRadioButton.class) {
            return RADIO_BUTTON;
        } else if (clazz == JScrollBar.class) {
            return SCROLL_BAR;
        } else if (clazz == JScrollPane.class) {
            return SCROLL_PANE;
        } else if (clazz == JSeparator.class) {
            return SEPARATOR;
        } else if (clazz == JSlider.class) {
            return SLIDER;
        } else if (clazz == JSpinner.class) {
            return SPINNER;
        } else if (clazz == JSplitPane.class) {
            return SPLIT_PANE;
        } else if (clazz == JTabbedPane.class) {
            return TABBED_PANE;
        } else if (clazz == JTextArea.class) {
            return TEXT_AREA;
        } else if (clazz == JTextField.class) {
            return TEXT_FIELD;
        } else if (clazz == JTextPane.class) {
            return TEXT_PANE;
        } else if (clazz == JTree.class) {
            return TREE;
        }

        return null;
    }
}