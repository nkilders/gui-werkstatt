package de.noah.guiwerkstatt.property;

import de.noah.guiwerkstatt.GuiWerkstatt;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractProperty<$COMP extends Container, $VAL> {
    private final String name;
    protected final $COMP component;
    private final AbstractProperty parent;
    protected AbstractProperty[] children;

    private boolean edited;
    private boolean expanded;

    public AbstractProperty(String name, $COMP component, AbstractProperty parent) {
        this.name = name;
        this.component = component;
        this.parent = parent;
        this.children = null;

        this.edited = false;
        this.expanded = false;

        setVal(getValue());
    }

    public abstract $VAL getValue();

    protected abstract void setVal($VAL value);

    public abstract String getCodeLine();

    public abstract JComponent getEditor();

    public String getName() {
        return name;
    }

    public void setValue($VAL value) {
        this.edited = true;
        setVal(value);

        component.repaint();
        if(GuiWerkstatt.getInstance().getGui() != null) {
            GuiWerkstatt.getInstance().getGui().getEditor().repaint();
        }
    }

    public AbstractProperty getParent() {
        return parent;
    }

    public AbstractProperty[] getChildren() {
        return children;
    }

    public boolean isEdited() {
        return edited;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}