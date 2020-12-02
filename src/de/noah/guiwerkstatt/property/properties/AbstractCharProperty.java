package de.noah.guiwerkstatt.property.properties;

import de.noah.guiwerkstatt.property.AbstractProperty;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public abstract class AbstractCharProperty<$COMP extends Container> extends AbstractProperty<$COMP, Character> {
    private final JTextField editor;

    public AbstractCharProperty(String name, $COMP component, AbstractProperty parent) {
        super(name, component, parent);

        this.editor = new JTextField();
        this.editor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                setValue(e.getKeyChar());
            }
        });
    }

    @Override
    public void setValue(Character value) {
        super.setValue(value);
        editor.setText("");
    }

    @Override
    public JTextField getEditor() {
        return editor;
    }
}
