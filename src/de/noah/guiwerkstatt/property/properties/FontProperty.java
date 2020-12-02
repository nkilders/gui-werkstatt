package de.noah.guiwerkstatt.property.properties;

import de.noah.guiwerkstatt.gui.components.FontChooser;
import de.noah.guiwerkstatt.property.AbstractProperty;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FontProperty extends AbstractProperty<Container, Font> {
    private final JTextField editor;

    public FontProperty(Container component) {
        super("Font", component, null);

        this.editor = new JTextField();
        this.editor.setEditable(false);
        this.editor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FontChooser fc = new FontChooser(editor);
                fc.setFont(getValue());
                fc.setOkListener(f -> {
                    setValue(fc.getFont());
                });
                fc.setVisible(true);
            }
        });
    }

    @Override
    public Font getValue() {
        return component.getFont();
    }

    @Override
    public void setValue(Font value) {
        super.setValue(value);

        String name = value.getName();
        int size = value.getSize();
        String style = "Plain";

        if (value.getStyle() == Font.BOLD) {
            style = "Bold";
        } else if (value.getStyle() == Font.ITALIC) {
            style = "Italic";
        } else if (value.getStyle() == Font.BOLD + Font.ITALIC) {
            style = "Bold + Italic";
        }

        editor.setText(String.format("%s, %s, %s", name, size, style));
    }

    @Override
    protected void setVal(Font value) {
        component.setFont(value);
    }

    @Override
    public String getCodeLine() {
        final Font f = getValue();

        String style = "Font.PLAIN";
        if (f.isBold() && f.isItalic())
            style = "Font.BOLD + Font.ITALIC";
        else if (f.isBold())
            style = "Font.BOLD";
        else if (f.isItalic())
            style = "Font.ITALIC";

        return String.format(".setFont(new Font(\"%s\", %s, %s));", f.getFontName(), style, f.getSize());
    }

    @Override
    public JComponent getEditor() {
        return editor;
    }
}