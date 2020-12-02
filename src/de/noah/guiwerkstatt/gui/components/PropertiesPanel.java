package de.noah.guiwerkstatt.gui.components;

import de.noah.guiwerkstatt.gui.Gui;
import de.noah.guiwerkstatt.property.AbstractProperty;
import de.noah.guiwerkstatt.property.PropertyList;
import de.noah.guiwerkstatt.utility.GraphicsUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PropertiesPanel extends ScrollPanel {
    private final Gui gui;

    private PropertyList current;

    public PropertiesPanel(Gui gui) {
        this.gui = gui;
        this.current = null;
    }

    public void setCurrent(PropertyList list) {
        this.current = list;

        for (Component c : getComponents()) {
            remove(c);
        }

        resetScrollOffset();

        int einsInt = 0;
        final FontMetrics fm = getFontMetrics(getFont());
        for (AbstractProperty p : current.getAllProperties()) {
            einsInt = Math.max(einsInt, fm.stringWidth(p.getName()) + 30);
        }

        int i = 0;
        for (AbstractProperty p : current.getAllProperties()) {
            if (p.getParent() == null) {
                ExpandableLabel el = new ExpandableLabel(p.getName());
                el.setBounds(0, i * 30, einsInt, 30);
                add(el);

                JComponent e = p.getEditor();
                e.setBounds(einsInt, i * 30, getWidth() - einsInt, 30);
                add(e);

                i++;

                if (p.getChildren() != null) {
                    if (p.getChildren().length > 0) {
                        el.setDrawArrow(true);
                        el.setExpanded(p.isExpanded());
                        el.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                p.setExpanded(!p.isExpanded());
                                setCurrent(current);
                            }
                        });

                        if (p.isExpanded()) {
                            for (AbstractProperty c : p.getChildren()) {
                                JLabel l = new JLabel(c.getName());
                                l.setBounds(22, i * 30, einsInt, 30);
                                add(l);

                                e = c.getEditor();
                                e.setBounds(einsInt, i * 30, getWidth() - einsInt, 30);
                                add(e);

                                i++;
                            }
                        }
                    }
                }
            }
        }
    }

    private class ExpandableLabel extends JLabel {
        private boolean drawArrow;
        private boolean expanded;

        public ExpandableLabel(String text) {
            super(text);

            this.drawArrow = false;
            this.expanded = false;
        }

        public ExpandableLabel() {
            this("");
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            final Graphics2D g = GraphicsUtil.castAndSetupRHs(graphics);

            graphics.translate(20, 0);
            super.paintComponent(graphics);
            graphics.translate(-20, 0);

            if (drawArrow) {
                g.setColor(Color.BLACK);

                if (expanded) {
                    g.drawLine(2, 12, 7, 20);
                    g.drawLine(7, 20, 12, 12);
                } else {
                    g.drawLine(4, 10, 12, 15);
                    g.drawLine(12, 15, 4, 20);
                }
            }
        }

        public boolean isExpanded() {
            return expanded;
        }

        public void setExpanded(boolean expanded) {
            this.expanded = expanded;
            repaint();
        }

        public boolean isDrawArrow() {
            return drawArrow;
        }

        public void setDrawArrow(boolean drawArrow) {
            this.drawArrow = drawArrow;
        }
    }
}