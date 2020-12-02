package de.noah.guiwerkstatt.property.properties;

import java.awt.*;

public class LocationProperty extends AbstractStringProperty<Container> {

    public LocationProperty(Container component) {
        super("Location", component, null);

        children = new AbstractIntProperty[]{
                new AbstractIntProperty<Container>("X", component, this) {

                    @Override
                    public Integer getValue() {
                        return component.getX();
                    }

                    @Override
                    protected void setVal(Integer value) {
                        component.setLocation(value, component.getY());
                        LocationProperty.this.getEditor().setText(String.format("[%s, %s]", value, component.getY()));
                    }

                    @Override
                    public String getCodeLine() {
                        return null;
                    }
                },
                new AbstractIntProperty<Container>("Y", component, this) {

                    @Override
                    public Integer getValue() {
                        return component.getY();
                    }

                    @Override
                    protected void setVal(Integer value) {
                        component.setLocation(component.getX(), value);
                        LocationProperty.this.getEditor().setText(String.format("[%s, %s]", component.getX(), value));
                    }

                    @Override
                    public String getCodeLine() {
                        return null;
                    }
                }
        };
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public void setValue(String value) {
        if (value != null) {
            if (value.startsWith("[") && value.contains(",") && value.endsWith("]")) {
                final String[] split = value.replace("[", "").replace("]", "").replace(" ", "").split(",");

                if (split.length == 2) {
                    try {
                        int x = Integer.parseInt(split[0]);
                        int y = Integer.parseInt(split[1]);

                        children[0].setValue(x);
                        children[1].setValue(y);
                        super.setValue(value);
                    } catch (Exception ex) {
                    }
                }
            }
        }
    }

    @Override
    protected void setVal(String value) {
    }

    @Override
    public String getCodeLine() {
        return String.format(".setLocation(%s, %s);", children[0].getValue(), children[1].getValue());
    }
}
