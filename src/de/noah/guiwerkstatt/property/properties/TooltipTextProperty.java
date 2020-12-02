package de.noah.guiwerkstatt.property.properties;

import javax.swing.*;

public class TooltipTextProperty extends AbstractStringProperty<JComponent> {

    public TooltipTextProperty(JComponent component) {
        super("Tooltip Text", component, null);
    }

    @Override
    public String getValue() {
        return component.getToolTipText();
    }

    @Override
    protected void setVal(String value) {
        component.setToolTipText(value);
    }

    @Override
    public String getCodeLine() {
        return String.format(".setToolTipText(\"%s\");", getValue());
    }
}