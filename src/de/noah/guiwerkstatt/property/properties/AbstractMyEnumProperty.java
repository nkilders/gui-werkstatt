package de.noah.guiwerkstatt.property.properties;

import de.noah.guiwerkstatt.property.AbstractProperty;
import de.noah.guiwerkstatt.utility.MyEnumEntry;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractMyEnumProperty<$COMP extends Container, $VAL> extends AbstractProperty<$COMP, $VAL> {
    private final JComboBox<String> editor;
    private final MyEnumEntry<$VAL>[] entries;
    private boolean ignoreNextListenerCall = false;

    public AbstractMyEnumProperty(String name, $COMP component, AbstractProperty parent, MyEnumEntry<$VAL>[] entries) {
        super(name, component, parent);

        this.entries = entries;
        this.editor = new JComboBox<>();
        for (MyEnumEntry d : entries) {
            editor.addItem(d.key);
        }
        this.editor.addItemListener(e -> {
            if (ignoreNextListenerCall) {
                ignoreNextListenerCall = false;
            } else {
                setValue(entries[editor.getSelectedIndex()].value);
            }
        });
    }

    @Override
    public void setValue($VAL value) {
        for (int i = 0; i < entries.length; i++) {
            if (entries[i].value.equals(value)) {
                ignoreNextListenerCall = true;
                editor.setSelectedIndex(i);
                super.setValue(value);

                break;
            }
        }
    }

    @Override
    public JComboBox<String> getEditor() {
        return editor;
    }
}