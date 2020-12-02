package de.noah.guiwerkstatt.property.properties;

import de.noah.guiwerkstatt.gui.components.AComponent;

public class VarNameProperty extends AbstractStringProperty<AComponent> {

    public VarNameProperty(AComponent component) {
        super("Var Name", component, null);
    }

    @Override
    public String getValue() {
        return component.getVarName();
    }

    @Override
    protected void setVal(String value) {
        component.setVarName(value);
    }

    @Override
    public String getCodeLine() {
        return null;
    }
}