package de.noah.guiwerkstatt.gui.components;

import de.noah.guiwerkstatt.GuiWerkstatt;
import de.noah.guiwerkstatt.code.CodeGenerator;
import de.noah.guiwerkstatt.gui.Gui;
import de.noah.guiwerkstatt.utility.Duplicator;
import de.noah.guiwerkstatt.utility.Finals;
import de.noah.guiwerkstatt.utility.GraphicsUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;

public class EditorPanel extends JComponent implements Finals {
    private final Gui gui;

    private final int CT_MOVE = 1;
    private final int CT_SCALE_N = 10;
    private final int CT_SCALE_NE = 11;
    private final int CT_SCALE_E = 12;
    private final int CT_SCALE_SE = 13;
    private final int CT_SCALE_S = 14;
    private final int CT_SCALE_SW = 15;
    private final int CT_SCALE_W = 16;
    private final int CT_SCALE_NW = 17;

    private AComponent builder;

    private AComponent hovered;
    private AComponent focused;
    private AComponent clipboard;

    private int clickType = -1;
    private Point moveAnchor = new Point(0, 0);
    private Point focusAnchor = new Point(0, 0);
    private boolean movingFocused = false;
    private String tooltip = "";

    public EditorPanel(Gui gui) {
        this.gui = gui;

        JFrame frame = new JFrame();
        AComponent window = new AComponent(frame);
        window.setProperty("Title", GuiWerkstatt.NAME);
        window.setProperty("Size", "[400,200]");
        window.setSize(400, 200);
        window.setLocation(10, 10);
        window.setProperty("Var Name", "window");

        AComponent acont = new AComponent(frame.getContentPane());
        window.add(acont);

        add(builder = window);
        acont.setProperty("Var Name", "content");

        gui.getOutline().updateOutline(builder);

        gui.getJMenuBar().getMenu(0).getItem(0).addActionListener(e -> {
            final String code = CodeGenerator.generate(builder);

            JDialog d = new JDialog(gui, "Output");
            d.setSize(600, 400);
            d.setLocationRelativeTo(null);

            final Container content = d.getContentPane();
            content.setLayout(new BorderLayout());

            JScrollPane scroll = new JScrollPane(new JTextArea(code));
            content.add(scroll, SwingConstants.CENTER);

            d.setVisible(true);
        });

        final MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleMousePressed(e, null);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                handleMouseReleased(e, null);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                handleMouseDragged(e, null);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                handleMouseMoved(e, null);
            }
        };
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    @Override
    protected void paintChildren(Graphics graphics) {
        super.paintChildren(graphics);
        final Graphics2D g = GraphicsUtil.castAndSetupRHs(graphics);

        if (focused != null) {
            if (!focused.getVarName().equals(getContent().getVarName())) {
                final Point pos = focused.getGlobalPos();

                int ix = moveAnchor.x - focusAnchor.x;
                int iy = moveAnchor.y - focusAnchor.y;

                if (movingFocused) {
                    g.translate(ix, iy);
                    focused.paint(g);
                    g.translate(-ix, -iy);
                }

                final int x = (movingFocused ? ix : pos.x);
                final int y = (movingFocused ? iy : pos.y);
                final int w = focused.getWidth();
                final int h = focused.getHeight();

                if (movingFocused) {
                    AComponent comp = getCompAt(moveAnchor);

                    if (comp != null) {
                        final Point p = comp.getGlobalPos();

                        g.setColor(Color.GREEN);
                        g.drawRect(p.x, p.y, comp.getWidth() - 1, comp.getHeight() - 1);
                    }
                } else {
                    if (focused.getParent() instanceof AComponent) {
                        g.setStroke(STROKE_DASHED);

                        Rectangle r = new Rectangle(x - 3, y - 3, w + 5, h + 5);

                        AComponent parent = (AComponent) focused.getParent();
                        Rectangle p = parent.getBounds();
                        p.setLocation(parent.getGlobalPos());

                        Area in = new Area(r);
                        in.intersect(new Area(p));

                        Area out = new Area(r);
                        out.subtract(new Area(p));

                        g.setColor(Color.BLACK);
                        g.draw(in);
                        g.setColor(Color.RED);
                        g.draw(out);

                        g.setStroke(STROKE_DEFAULT);
                    } else {
                        g.setStroke(STROKE_DASHED);
                        g.setColor(Color.BLACK);
                        g.drawRect(x - 3, y - 3, w + 5, h + 5);
                        g.setStroke(STROKE_DEFAULT);
                    }

                    g.setColor(Color.WHITE);
                    g.fillRect(x - 5, y - 5, 4, 4);
                    g.fillRect(x + (w / 2) - 2, y - 5, 4, 4);
                    g.fillRect(x + w, y - 5, 4, 4);
                    g.fillRect(x + w, y + (h / 2) - 2, 4, 4);
                    g.fillRect(x + w, y + h, 4, 4);
                    g.fillRect(x + (w / 2) - 2, y + h, 4, 4);
                    g.fillRect(x - 5, y + h, 4, 4);
                    g.fillRect(x - 5, y + (h / 2) - 2, 4, 4);

                    g.setColor(Color.BLACK);
                    g.drawRect(x - 5, y - 5, 4, 4);
                    g.drawRect(x + (w / 2) - 2, y - 5, 4, 4);
                    g.drawRect(x + w, y - 5, 4, 4);
                    g.drawRect(x + w, y + (h / 2) - 2, 4, 4);
                    g.drawRect(x + w, y + h, 4, 4);
                    g.drawRect(x + (w / 2) - 2, y + h, 4, 4);
                    g.drawRect(x - 5, y + h, 4, 4);
                    g.drawRect(x - 5, y + (h / 2) - 2, 4, 4);
                }
            }
        }

        if (!tooltip.isEmpty()) {
            final int x = moveAnchor.x + 15;
            final int y = moveAnchor.y;
            final int w = g.getFontMetrics().stringWidth(tooltip) + 4;
            final int h = g.getFontMetrics().getHeight() + 4;

            g.setColor(new Color(0x34495e));
            g.fillRect(x, y, w, h);
            g.setColor(new Color(0x1F3040));
            g.drawRect(x, y, w, h);
            g.setColor(Color.WHITE);
            g.drawString(tooltip, x + 2, y + h - 5);
        }
    }

    public void handleMousePressed(MouseEvent e, AComponent comp) {
        if (gui.getToolbox().getSelected() != null)
            return;

        if (comp != null)
            e.translatePoint(comp.getGlobalPos().x, comp.getGlobalPos().y);

        final int ex = e.getX();
        final int ey = e.getY();

        if (SwingUtilities.isLeftMouseButton(e)) {
            if (focused == null) {
                clickType = CT_MOVE;
                updateFocused(comp);
            } else {
                final int x = focused.getGlobalPos().x;
                final int y = focused.getGlobalPos().y;
                final int w = focused.getWidth();
                final int h = focused.getHeight();

                if (ex >= x && ey >= y && ex <= x + w && ey <= y + h) {
                    clickType = CT_MOVE;
                } else if (ex >= x - 5 && ey >= y - 5 && ex < x && ey < y) {
                    clickType = CT_SCALE_NW;
                } else if (ex >= x && ey >= y - 5 && ex < x + w && ey < y) {
                    clickType = CT_SCALE_N;
                } else if (ex >= x + w && ey >= y - 5 && ex < x + w + 5 && ey < y) {
                    clickType = CT_SCALE_NE;
                } else if (ex >= x + w && ey >= y && ex < x + w + 5 && ey < y + h) {
                    clickType = CT_SCALE_E;
                } else if (ex >= x + w && ey >= y + h && ex < x + w + 5 && ey < y + h + 5) {
                    clickType = CT_SCALE_SE;
                } else if (ex >= x && ey >= y + h && ex < x + w && ey < y + h + 5) {
                    clickType = CT_SCALE_S;
                } else if (ex >= x - 5 && ey >= y + h && ex < x && ey < y + h + 5) {
                    clickType = CT_SCALE_SW;
                } else if (ex >= x - 5 && ey >= y && ex < x && ey < y + h) {
                    clickType = CT_SCALE_W;
                } else {
                    updateFocused(hovered);
                }
            }
        }
    }

    public void handleMouseReleased(MouseEvent e, AComponent comp) {
        if (comp != null)
            e.translatePoint(comp.getGlobalPos().x, comp.getGlobalPos().y);

        final int ex = e.getX();
        final int ey = e.getY();

        clickType = -1;

        if (SwingUtilities.isLeftMouseButton(e)) {
            if (gui.getToolbox().getSelected() != null) {
                AComponent c = getCompAt(e.getPoint());

                if (c != null) {
                    AComponent newC = createComponent(gui.getToolbox().getSelected().getTool());
                    newC.setLocation(ex - c.getGlobalPos().x, ey - c.getGlobalPos().y);
                    newC.setProperty("Location", String.format("[%s, %s]", newC.getX(), newC.getY()));
                    newC.setProperty("Size", String.format("[%s, %s]", newC.getWidth(), newC.getHeight()));
                    c.add(newC);

                    updateFocused(newC);
                    gui.getToolbox().setSelected(null);
                    gui.getOutline().updateOutline(builder);
                    repaint();
                }
            } else {
                if (movingFocused) {
                    movingFocused = false;

                    if (focused == builder) {
                        focused.setLocation(ex - focusAnchor.x, ey - focusAnchor.y);
                    } else {
                        AComponent c = getCompAt(e.getPoint());
                        focused.setLocation(ex - c.getGlobalPos().x - focusAnchor.x, ey - c.getGlobalPos().y - focusAnchor.y);

                        if (c != null) {
                            focused.getParent().remove(focused);
                            c.add(focused);
                        }
                    }

                    gui.getOutline().updateOutline(builder);
                    repaint();
                }
            }
        } else if (SwingUtilities.isRightMouseButton(e)) {
            if (gui.getToolbox().getSelected() != null) {
                gui.getToolbox().setSelected(null);
            }

            if (comp != null) {
                if (!(comp.getComponent() instanceof JFrame)) {
                    JPopupMenu popupMenu = new JPopupMenu();
                    JMenuItem item;

                    popupMenu.add(item = new JMenuItem("Ausschneiden"));
                    item.setEnabled(comp != getContent());
                    item.addActionListener(ev -> {
                        comp.getParent().remove(comp);
                        clipboard = comp;
                    });

                    popupMenu.add(item = new JMenuItem("Kopieren"));
                    item.addActionListener(ev -> {
                        clipboard = comp;
                    });

                    popupMenu.add(item = new JMenuItem("Einf\u00fcgen"));
                    item.setEnabled(clipboard != null);
                    item.addActionListener(ev -> {
                        AComponent c = new AComponent((JComponent) Duplicator.duplicate(clipboard.getComponent()));
                        c.setLocation(ex - comp.getGlobalPos().x, ey - comp.getGlobalPos().y);
                        comp.add(c);

                        updateFocused(c);
                        gui.getOutline().updateOutline(builder);
                    });

                    popupMenu.add(item = new JMenuItem("L\u00f6schen"));
                    item.setEnabled(comp != getContent());
                    item.addActionListener(ev -> {
                        comp.getParent().remove(comp);
                    });

                    popupMenu.show(EditorPanel.this, ex, ey);
                }
            }
        }
    }

    public void handleMouseMoved(MouseEvent e, AComponent comp) {
        if (comp != null)
            e.translatePoint(comp.getGlobalPos().x, comp.getGlobalPos().y);

        final int ex = e.getX();
        final int ey = e.getY();

        if (focused != null) {
            final int x = focused.getGlobalPos().x;
            final int y = focused.getGlobalPos().y;
            final int w = focused.getWidth();
            final int h = focused.getHeight();

            if (ex >= x && ey >= y && ex <= x + w && ey <= y + h) {
                updateCursor(CURSOR_MOVE);
            } else if (ex >= x - 5 && ey >= y - 5 && ex < x && ey < y) {
                updateCursor(CURSOR_RESIZE_NW);
            } else if (ex >= x && ey >= y - 5 && ex < x + w && ey < y) {
                updateCursor(CURSOR_RESIZE_N);
            } else if (ex >= x + w && ey >= y - 5 && ex < x + w + 5 && ey < y) {
                updateCursor(CURSOR_RESIZE_NE);
            } else if (ex >= x + w && ey >= y && ex < x + w + 5 && ey < y + h) {
                updateCursor(CURSOR_RESIZE_E);
            } else if (ex >= x + w && ey >= y + h && ex < x + w + 5 && ey < y + h + 5) {
                updateCursor(CURSOR_RESIZE_SE);
            } else if (ex >= x && ey >= y + h && ex < x + w && ey < y + h + 5) {
                updateCursor(CURSOR_RESIZE_S);
            } else if (ex >= x - 5 && ey >= y + h && ex < x && ey < y + h + 5) {
                updateCursor(CURSOR_RESIZE_SW);
            } else if (ex >= x - 5 && ey >= y && ex < x && ey < y + h) {
                updateCursor(CURSOR_RESIZE_W);
            } else {
                updateCursor(CURSOR_DEFAULT);
            }
        }

        tooltip = "";

        moveAnchor = e.getPoint();
        repaint();
    }

    public void handleMouseDragged(MouseEvent e, AComponent comp) {
        if (comp != null)
            e.translatePoint(comp.getGlobalPos().x, comp.getGlobalPos().y);

        final int ex = e.getX();
        final int ey = e.getY();

        if (SwingUtilities.isLeftMouseButton(e)) {
            if (focused != null) {
                if (focused != getContent()) {
                    final int gx = focused.getGlobalPos().x;
                    final int gy = focused.getGlobalPos().y;

                    final int x = focused.getX();
                    final int y = focused.getY();
                    final int w = focused.getWidth();
                    final int h = focused.getHeight();

                    switch (clickType) {
                        case CT_MOVE:
                            if (!movingFocused) {
                                movingFocused = true;
                                focusAnchor.setLocation(ex - gx, ey - gy);
                                focused.setLocation(-Integer.MAX_VALUE, -Integer.MAX_VALUE);
                            }
                            break;

                        case CT_SCALE_NW:
                            focused.setSize(w + (gx - ex), h + (gy - ey));
                            focused.setLocation(x - (gx - ex), y - (gy - ey));
                            break;

                        case CT_SCALE_N:
                            focused.setSize(w, h + (gy - ey));
                            focused.setLocation(x, y - (gy - ey));
                            break;

                        case CT_SCALE_NE:
                            focused.setSize(ex - gx, h + (gy - ey));
                            focused.setLocation(x, y - (gy - ey));
                            break;

                        case CT_SCALE_E:
                            focused.setSize(ex - gx, h);
                            break;

                        case CT_SCALE_SE:
                            focused.setSize(ex - gx, ey - gy);
                            break;

                        case CT_SCALE_S:
                            focused.setSize(w, ey - gy);
                            break;

                        case CT_SCALE_SW:
                            focused.setSize(w + (gx - ex), ey - gy);
                            focused.setLocation(x - (gx - ex), y);
                            break;

                        case CT_SCALE_W:
                            focused.setSize(w + (gx - ex), h);
                            focused.setLocation(x - (gx - ex), y);
                            break;
                    }

                    final int newX = movingFocused ? moveAnchor.x - focusAnchor.x : (int) focusAnchor.getX();
                    final int newY = movingFocused ? moveAnchor.y - focusAnchor.y : (int) focusAnchor.getY();

                    if (focused.getComponent() instanceof JFrame) {
                        getContent().setBounds(1, 31, focused.getWidth() - 2, focused.getHeight() - 32);
                    }

                    focused.setProperty("Location", String.format("[%s, %s]", newX, newY));
                    focused.setProperty("Size", String.format("[%s, %s]", focused.getWidth(), focused.getHeight()));

                    tooltip = String.format("%s;%s %sx%s", newX, newY, focused.getWidth(), focused.getHeight());
                }
            }
        }

        moveAnchor = e.getPoint();
        repaint();
    }

    public AComponent getContent() {
        return (AComponent) builder.getComponents()[0];
    }

    public AComponent getHovered() {
        return hovered;
    }

    public void updateHovered(AComponent component) {
        hovered = component;
        repaint();
    }

    public AComponent getFocused() {
        return focused;
    }

    public void updateFocused(AComponent component) {
        focused = component;
        gui.getOutline().repaint();
        gui.getProperties().setCurrent(component.getProperties());
        repaint();
    }

    private void updateCursor(Cursor cursor) {
        if (getCursor() != cursor)
            setCursor(cursor);

        for (AComponent c : AComponent.getAll()) {
            if (c.getCursor() != cursor) {
                c.setCursor(cursor);
            }
        }
    }

    private AComponent getCompAt(Point point) {
        AComponent ret = builder;

        while (true) {
            AComponent temp = ret;

            for (Component component : ret.getComponents()) {
                if (component instanceof AComponent) {
                    final AComponent c = (AComponent) component;

                    if (c.isIn(point)) {
                        ret = c;
                        break;
                    }
                }
            }

            if (temp == ret) {
                break;
            }
        }

        return ret;
    }

    private AComponent createComponent(Class<? extends JComponent> clazz) {
        AComponent ret = null;

        try {
            JComponent comp = clazz.newInstance();
            ret = new AComponent(comp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (ret.getComponent() instanceof JPanel) {
            ret.setSize(150, 100);
        } else {
            ret.setSize(100, 20);
        }

        return ret;
    }
}