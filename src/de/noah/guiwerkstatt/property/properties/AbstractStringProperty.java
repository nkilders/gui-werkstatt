package de.noah.guiwerkstatt.property.properties;

import de.noah.guiwerkstatt.property.AbstractProperty;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public abstract class AbstractStringProperty<$COMP extends Container> extends AbstractProperty<$COMP, String> {
    private final JTextField editor;

    public AbstractStringProperty(String name, $COMP component, AbstractProperty parent) {
        super(name, component, parent);

        this.editor = new JTextField();
        this.editor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    setValue(editor.getText());
                }
            }
        });
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
        editor.setText(value);
    }

    @Override
    public JTextField getEditor() {
        return editor;
    }
}