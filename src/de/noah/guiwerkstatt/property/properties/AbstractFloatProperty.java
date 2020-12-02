package de.noah.guiwerkstatt.property.properties;

import de.noah.guiwerkstatt.property.AbstractProperty;
import de.noah.guiwerkstatt.utility.Finals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public abstract class AbstractFloatProperty<$COMP extends Container> extends AbstractProperty<$COMP, Float> implements Finals {
    private final JTextField editor;

    public AbstractFloatProperty(String name, $COMP component, AbstractProperty parent) {
        super(name, component, parent);

        this.editor = new JTextField();
        this.editor.setText(getValue().toString());
        this.editor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                StringBuffer sb = new StringBuffer();
                boolean b = false;
                for (char c : editor.getText().toCharArray()) {
                    for (char d : NUMBERS) {
                        if (c == d) {
                            sb.append(c);
                        }
                    }

                    if (c == '.' && !b) {
                        sb.append(c);
                        b = true;
                    }
                }

                if (!editor.getText().equals(sb.toString())) {
                    editor.setText(sb.toString());
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    setValue(Float.parseFloat("0" + editor.getText()));
                }
            }
        });
    }

    @Override
    public void setValue(Float value) {
        super.setValue(value);
        editor.setText(value.toString());
    }

    @Override
    public JTextField getEditor() {
        return editor;
    }
}
