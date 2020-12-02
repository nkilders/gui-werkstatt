package de.noah.guiwerkstatt.gui.components;

import de.noah.guiwerkstatt.utility.GraphicsUtil;

import javax.swing.*;
import java.awt.*;

public class ColorDisplay extends JComponent {
    private Color color;

    public ColorDisplay(Color color) {
        if (color == null)
            color = Color.WHITE;

        this.color = color;
    }

    public ColorDisplay() {
        this(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        final Graphics2D g = GraphicsUtil.castAndSetupRHs(graphics);

        final int h = getHeight() - 4;

        g.setColor(color);
        g.fillRect(2, 2, h, h);

        g.setColor(Color.BLACK);
        g.drawRect(2, 2, h, h);

        g.drawString(String.format("%s, %s, %s", color.getRed(), color.getGreen(), color.getBlue()),
                4 + h + 5,
                (getHeight() / 2) + (g.getFontMetrics().getHeight() / 3));
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        if (color == null)
            color = Color.WHITE;

        this.color = color;
        repaint();
    }
}