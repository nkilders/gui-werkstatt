package de.noah.guiwerkstatt.property.properties;

import de.noah.guiwerkstatt.property.AbstractProperty;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractBooleanProperty<$COMP extends Container> extends AbstractProperty<$COMP, Boolean> {
    private final JCheckBox editor;

    public AbstractBooleanProperty(String name, $COMP component, AbstractProperty parent) {
        super(name, component, parent);

        this.editor = new JCheckBox();
        this.editor.setSelected(getValue());
        this.editor.addItemListener(e -> setValue(editor.isSelected()));
    }

    @Override
    public void setValue(Boolean value) {
        super.setValue(value);
        editor.setSelected(value);
    }

    @Override
    public JCheckBox getEditor() {
        return editor;
    }
}
