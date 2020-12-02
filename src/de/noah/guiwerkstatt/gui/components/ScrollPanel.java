package de.noah.guiwerkstatt.gui.components;

import javax.swing.*;
import java.awt.*;

public class ScrollPanel extends JComponent {
    private Point scrollOffset = new Point(0, 0);
    private int scrollSize = 5;

    public ScrollPanel() {
        addMouseWheelListener(e -> {
            if (e.getWheelRotation() == 1) {
                if (e.isShiftDown()) {
                    for (Component c : getComponents()) {
                        if (c.getX() + c.getWidth() > getWidth() - (scrollSize * scrollOffset.x)) {
                            moveChildren(-1, 0);
                            repaint();

                            break;
                        }
                    }
                } else {
                    for (Component c : getComponents()) {
                        if (c.getY() + c.getHeight() > getHeight() - (scrollSize * scrollOffset.y)) {
                            moveChildren(0, -1);
                            repaint();

                            break;
                        }
                    }
                }
            } else if (e.getWheelRotation() == -1) {
                if (e.isShiftDown()) {
                    if (scrollOffset.x < 0) {
                        moveChildren(1, 0);
                        repaint();
                    }
                } else {
                    if (scrollOffset.y < 0) {
                        moveChildren(0, 1);
                        repaint();
                    }
                }
            }
        });
    }

    private void moveChildren(int xDirection, int yDirection) {
        scrollOffset.translate(xDirection, yDirection);

        for (Component c : getComponents()) {
            Point p = c.getLocation();
            p.translate(xDirection * scrollSize, yDirection * scrollSize);
            c.setLocation(p);
        }
    }

    public Point getScrollOffset() {
        return scrollOffset;
    }

    public void resetScrollOffset() {
        if(scrollOffset.x > 0) {
            for(int i = scrollOffset.x; i > 0; i--) {
                moveChildren(-1, 0);
            }
        } else if(scrollOffset.x < 0) {
            for(int i = scrollOffset.x; i < 0; i++) {
                moveChildren(1, 0);
            }
        }

        if(scrollOffset.y > 0) {
            for(int i = scrollOffset.y; i > 0; i--) {
                moveChildren(0, -1);
            }
        } else if(scrollOffset.y < 0) {
            for(int i = scrollOffset.y; i < 0; i++) {
                moveChildren(0, 1);
            }
        }

        repaint();
    }

    public int getScrollSize() {
        return scrollSize;
    }
}