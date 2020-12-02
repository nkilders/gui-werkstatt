package de.noah.guiwerkstatt.gui.components;

import de.noah.guiwerkstatt.GuiWerkstatt;
import de.noah.guiwerkstatt.property.AbstractProperty;
import de.noah.guiwerkstatt.property.PropertyList;
import de.noah.guiwerkstatt.utility.Finals;
import de.noah.guiwerkstatt.utility.GraphicsUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class AComponent extends JComponent implements Finals {
    private static final List<AComponent> COMPONENTS = new ArrayList<>();

    private final Container component;
    private PropertyList properties;
    private String varName;

    private Point globalPos;

    public AComponent(Container component) {
        this.component = component;

        generateVarName();
        COMPONENTS.add(this);

        this.properties = new PropertyList(this);

        setBounds(component.getBounds());
        updateGlobalPos();

        final MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                GuiWerkstatt.getInstance().getGui().getEditor().handleMousePressed(e, AComponent.this);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                GuiWerkstatt.getInstance().getGui().getEditor().handleMouseReleased(e, AComponent.this);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                final EditorPanel editor = GuiWerkstatt.getInstance().getGui().getEditor();

                editor.updateHovered(AComponent.this);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                final EditorPanel editor = GuiWerkstatt.getInstance().getGui().getEditor();

                if (editor.getHovered() == AComponent.this) {
                    editor.updateHovered(null);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                GuiWerkstatt.getInstance().getGui().getEditor().handleMouseMoved(e, AComponent.this);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                GuiWerkstatt.getInstance().getGui().getEditor().handleMouseDragged(e, AComponent.this);
            }
        };

        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    public static List<AComponent> getAll() {
        return COMPONENTS;
    }

    public static AComponent getFromName(String name) {
        for (AComponent c : COMPONENTS) {
            if (c.getVarName().equals(name)) {
                return c;
            }
        }

        return null;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        final Graphics2D g = GraphicsUtil.castAndSetupRHs(graphics);

        if (component instanceof JFrame) {
            GraphicsUtil.drawJFrame(g, (JFrame) component);
        } else if (!(component instanceof JPanel || component instanceof JLabel)) {
            component.paint(graphics);
        } else if (!varName.equals(GuiWerkstatt.getInstance().getGui().getEditor().getContent().getVarName())) {
            g.setStroke(STROKE_DASHED);
            g.setColor(new Color(0, 0, 0, 100));
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            g.setStroke(STROKE_DEFAULT);
        }
    }

    private void generateVarName() {
        final String nameBase = component.getClass().getSimpleName();

        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            boolean taken = false;

            for (AComponent c : COMPONENTS) {
                if (c.varName.equalsIgnoreCase(nameBase + i)) {
                    taken = true;
                }
            }

            if (!taken) {
                varName = nameBase + i;
                return;
            }
        }
    }

    public Container getComponent() {
        return component;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public Point getGlobalPos() {
        return globalPos;
    }

    private void updateGlobalPos() {
        globalPos = getLocation();

        for (Container c = getParent(); c instanceof AComponent; c = c.getParent()) {
            globalPos.translate(c.getX(), c.getY());
        }

        for (Component c : getComponents()) {
            if (c instanceof AComponent) {
                ((AComponent) c).updateGlobalPos();
            }
        }
    }

    public boolean isIn(Point p) {
        final int x = globalPos.x;
        final int y = globalPos.y;
        final int w = getWidth();
        final int h = getHeight();

        return p.x >= x && p.y >= y && p.x < x + w && p.y < y + h;
    }

    public PropertyList getProperties() {
        return properties;
    }

    public void setProperty(String property, Object value) {
        AbstractProperty p = properties.getPropertyByName(property);

        if (p != null) {
            p.setValue(value);
        }
    }

    // ================================================================================

    @Override
    public Component add(Component comp) {
        super.add(comp);

        if (component instanceof JFrame) {
            if (comp != null) {
                comp.setBounds(1, 31, component.getWidth() - 2, component.getHeight() - 32);
            }
        }

        if (comp instanceof AComponent) {
            ((AComponent) comp).updateGlobalPos();
        }

        return comp;
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        component.setBounds(x, y, width, height);

        updateGlobalPos();
        repaint();
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        component.setSize(width, height);

        repaint();
    }

    @Override
    public void setSize(Dimension d) {
        super.setSize(d);
        component.setSize(d);

        repaint();
    }

    @Override
    public void setLocation(int x, int y) {
        super.setLocation(x, y);
        component.setLocation(x, y);

        updateGlobalPos();
    }
}