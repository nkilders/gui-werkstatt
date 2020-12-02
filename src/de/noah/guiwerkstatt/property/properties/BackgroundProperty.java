package de.noah.guiwerkstatt.property.properties;

import java.awt.*;

public class BackgroundProperty extends AbstractColorProperty<Container> {

    public BackgroundProperty(Container component) {
        super("Background", component, null);
    }

    @Override
    public Color getValue() {
        return component.getBackground();
    }

    @Override
    protected void setVal(Color value) {
        component.setBackground(value);
    }

    @Override
    public String getCodeLine() {
        final Color c = getValue();
        return String.format(".setBackground(%s, %s, %s);", c.getRed(), c.getGreen(), c.getBlue());
    }
}