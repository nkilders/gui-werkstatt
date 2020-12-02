package de.noah.guiwerkstatt.property.properties;

import java.awt.*;

public class FocusableProperty extends AbstractBooleanProperty<Container> {

    public FocusableProperty(Container component) {
        super("Focusable", component, null);
    }

    @Override
    public Boolean getValue() {
        return component.isFocusable();
    }

    @Override
    protected void setVal(Boolean value) {
        component.setFocusable(value);
    }

    @Override
    public String getCodeLine() {
        return String.format(".setFocusable(%s);", getValue());
    }
}