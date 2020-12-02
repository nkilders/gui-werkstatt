package de.noah.guiwerkstatt.property.properties;

import de.noah.guiwerkstatt.property.AbstractProperty;
import de.noah.guiwerkstatt.utility.Finals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public abstract class AbstractIntProperty<$COMP extends Container> extends AbstractProperty<$COMP, Integer> implements Finals {
    private final JTextField editor;

    public AbstractIntProperty(String name, $COMP component, AbstractProperty parent) {
        super(name, component, parent);

        this.editor = new JTextField();
        this.editor.setText(getValue().toString());
        this.editor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                StringBuffer sb = new StringBuffer();
                for (char c : editor.getText().toCharArray()) {
                    for (char d : NUMBERS) {
                        if (c == d) {
                            sb.append(c);
                        }
                    }
                }

                if (!editor.getText().equals(sb.toString())) {
                    editor.setText(sb.toString());
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    setValue(Integer.parseInt("0" + editor.getText()));
                }
            }
        });
    }

    @Override
    public void setValue(Integer value) {
        super.setValue(value);
        editor.setText(value.toString());
    }

    @Override
    public JTextField getEditor() {
        return editor;
    }
}