package de.noah.guiwerkstatt.gui.components;

import javax.swing.tree.DefaultMutableTreeNode;

public class ATreeNode extends DefaultMutableTreeNode {
    private final AComponent component;

    public ATreeNode(AComponent component) {
        super(component.getVarName());
        this.component = component;
    }

    public AComponent getComponent() {
        return component;
    }
}