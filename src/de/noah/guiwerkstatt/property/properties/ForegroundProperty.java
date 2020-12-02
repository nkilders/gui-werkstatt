package de.noah.guiwerkstatt.property.properties;

import java.awt.*;

public class ForegroundProperty extends AbstractColorProperty<Container> {

    public ForegroundProperty(Container component) {
        super("Foreground", component, null);
    }

    @Override
    public Color getValue() {
        return component.getForeground();
    }

    @Override
    protected void setVal(Color value) {
        component.setForeground(value);
    }

    @Override
    public String getCodeLine() {
        final Color c = getValue();
        return String.format(".setForeground(%s, %s, %s);", c.getRed(), c.getGreen(), c.getBlue());
    }
}