package de.noah.guiwerkstatt.gui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FontChooser extends JDialog {
    private static final String[] SIZES = {"2", "4", "6", "8", "10", "12", "14", "16", "18", "20", "22", "24", "30", "36", "48", "72"};

    private Font font;

    private ActionListener okListener;

    private JList fontList;
    private JList sizeList;
    private JCheckBox cbBold;
    private JCheckBox cbItalic;
    private JTextField textSample;

    public FontChooser(Component component) {
        super(JOptionPane.getFrameForComponent(component), true);
        setTitle("Schrift w\u00e4hlen");
        setSize(425, 435);
        setLocationRelativeTo(component);

        final Container content = getContentPane();
        content.setLayout(null);

        JButton btn;
        JScrollPane scroll;

        btn = new JButton("OK");
        btn.setBounds(10, 360, 80, 20);
        btn.addActionListener(e -> {
            createFont();
            if (okListener != null) {
                okListener.actionPerformed(new ActionEvent(FontChooser.this, 0, "OK"));
            }

            setVisible(false);
        });
        content.add(btn);

        btn = new JButton("Abbrechen");
        btn.setBounds(100, 360, 100, 20);
        btn.addActionListener(e -> {
            setVisible(false);
        });
        content.add(btn);

        fontList = new JList<>(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
        fontList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fontList.setSelectedIndex(0);
        fontList.addListSelectionListener(e -> createFont());
        scroll = new JScrollPane(fontList);
        scroll.setBounds(10, 10, 200, 250);
        content.add(scroll);

        sizeList = new JList<>(SIZES);
        sizeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sizeList.setSelectedIndex(5);
        sizeList.addListSelectionListener(e -> createFont());
        scroll = new JScrollPane(sizeList);
        scroll.setBounds(220, 10, 60, 250);
        content.add(scroll);

        cbBold = new JCheckBox("Bold");
        cbBold.setBounds(290, 10, 100, 20);
        cbBold.addActionListener(e -> createFont());
        content.add(cbBold);

        cbItalic = new JCheckBox("Italic");
        cbItalic.setBounds(290, 35, 100, 20);
        cbItalic.addActionListener(e -> createFont());
        content.add(cbItalic);

        textSample = new JTextField("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.");
        textSample.setBounds(10, 270, 380, 80);
        textSample.setHorizontalAlignment(SwingConstants.LEFT);
        content.add(textSample);

        createFont();
    }

    private void createFont() {
        String name = (String) fontList.getSelectedValue();
        int size = Integer.parseInt((String) sizeList.getSelectedValue());
        int style = 0;

        if (cbBold.isSelected())
            style += Font.BOLD;
        if (cbItalic.isSelected())
            style += Font.ITALIC;

        font = new Font(name, style, size);
        textSample.setFont(font);
    }

    @Override
    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;

        if (font != null) {
            for (int i = 0; i < fontList.getModel().getSize(); i++) {
                if (fontList.getModel().getElementAt(i).equals(font.getName())) {
                    fontList.setSelectedIndex(i);
                    break;
                }
            }

            for (int i = 0; i < sizeList.getModel().getSize(); i++) {
                if (sizeList.getModel().getElementAt(i).equals(String.valueOf(font.getSize()))) {
                    sizeList.setSelectedIndex(i);
                    break;
                }
            }

            cbBold.setSelected(font.isBold());
            cbItalic.setSelected(font.isItalic());
        }

        textSample.setFont(font);
    }

    public void setOkListener(ActionListener okListener) {
        this.okListener = okListener;
    }
}