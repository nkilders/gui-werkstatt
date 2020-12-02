package de.noah.guiwerkstatt.gui.components;

import de.noah.guiwerkstatt.gui.Gui;
import de.noah.guiwerkstatt.utility.Icons;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

public class OutlinePanel extends JTree {
    private final Gui gui;

    public OutlinePanel(Gui gui) {
        this.gui = gui;

        addTreeSelectionListener(e -> {
            if (e.getNewLeadSelectionPath() != null) {
                final Object obj = e.getNewLeadSelectionPath().getLastPathComponent();

                if (obj instanceof ATreeNode) {
                    final ATreeNode node = (ATreeNode) obj;

                    gui.getEditor().updateFocused(node.getComponent());
                }
            }
        });

        setCellRenderer(new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                if (value instanceof ATreeNode) {
                    final ATreeNode node = (ATreeNode) value;
                    final String val = value.toString();
                    final AComponent comp = AComponent.getFromName(val);

                    if (comp != null) {
                        boolean focus = false;

                        if (gui.getEditor() != null) {
                            if (gui.getEditor().getFocused() != null) {
                                if (comp.getVarName().equals(gui.getEditor().getFocused().getVarName())) {
                                    focus = true;
                                }
                            }
                        }

                        final Component ret = super.getTreeCellRendererComponent(tree, value, focus, expanded, leaf, row, false);
                        final Component c = node.getComponent().getComponent();

                        Icon i = Icons.getFromClass(c.getClass());
                        if (i != null)
                            setIcon(i);

                        return ret;
                    }
                }

                return super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            }
        });
    }

    public void updateOutline(AComponent frame) {
        setModel(new DefaultTreeModel(generateTreeNode(frame)));
        expandAll();
    }

    private DefaultMutableTreeNode generateTreeNode(AComponent component) {
        ATreeNode node = new ATreeNode(component);

        for (Component c : component.getComponents()) {
            if (c instanceof AComponent) {
                node.add(generateTreeNode((AComponent) c));
            }
        }

        return node;
    }

    private void expandAll() {
        for (int i = 0; i < getRowCount(); i++) {
            expandRow(i);
        }
    }
}