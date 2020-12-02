package de.noah.guiwerkstatt.property.properties;

import de.noah.guiwerkstatt.gui.components.AComponent;

public class SizeProperty extends AbstractStringProperty<AComponent> {

    public SizeProperty(AComponent component) {
        super("Size", component, null);

        children = new AbstractIntProperty[]{
                new AbstractIntProperty<AComponent>("Width", component, this) {

                    @Override
                    public Integer getValue() {
                        return component.getWidth();
                    }

                    @Override
                    protected void setVal(Integer value) {
                        component.setSize(value, component.getHeight());
                        SizeProperty.this.getEditor().setText(String.format("[%s, %s]", value, component.getHeight()));
                    }

                    @Override
                    public String getCodeLine() {
                        return null;
                    }
                },
                new AbstractIntProperty<AComponent>("Height", component, this) {

                    @Override
                    public Integer getValue() {
                        return component.getHeight();
                    }

                    @Override
                    protected void setVal(Integer value) {
                        component.setSize(component.getWidth(), value);
                        SizeProperty.this.getEditor().setText(String.format("[%s, %s]", component.getWidth(), value));
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
        return String.format(".setSize(%s, %s);", children[0].getValue(), children[1].getValue());
    }
}
