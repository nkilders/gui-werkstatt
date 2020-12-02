package de.noah.guiwerkstatt.property.properties;

import java.awt.*;

public class EnabledProperty extends AbstractBooleanProperty<Container> {

    public EnabledProperty(Container component) {
        super("Enabled", component, null);
    }

    @Override
    public Boolean getValue() {
        return component.isEnabled();
    }

    @Override
    protected void setVal(Boolean value) {
        component.setEnabled(value);
    }

    @Override
    public String getCodeLine() {
        return String.format(".setEnabled(%s);", getValue());
    }
}