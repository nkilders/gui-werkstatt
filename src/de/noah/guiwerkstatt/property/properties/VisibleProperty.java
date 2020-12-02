package de.noah.guiwerkstatt.property.properties;

import java.awt.*;

public class VisibleProperty extends AbstractBooleanProperty<Container> {

    public VisibleProperty(Container component) {
        super("Visible", component, null);
    }

    @Override
    public Boolean getValue() {
        return component.isVisible();
    }

    @Override
    protected void setVal(Boolean value) {
        component.setVisible(value);
    }

    @Override
    public String getCodeLine() {
        return String.format(".setVisible(%s);", getValue());
    }
}