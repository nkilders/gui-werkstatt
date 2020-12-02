package de.noah.guiwerkstatt.property.properties;

import javax.swing.text.JTextComponent;

public class EditableProperty<$COMP extends JTextComponent> extends AbstractBooleanProperty<$COMP> {

    public EditableProperty($COMP component) {
        super("Editable", component, null);
    }

    @Override
    public Boolean getValue() {
        return component.isEditable();
    }

    @Override
    protected void setVal(Boolean value) {
        component.setEditable(value);
    }

    @Override
    public String getCodeLine() {
        return String.format("setEditable(%s);", getValue().toString());
    }
}
