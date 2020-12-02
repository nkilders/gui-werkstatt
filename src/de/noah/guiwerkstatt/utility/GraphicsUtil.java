package de.noah.guiwerkstatt.utility;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class GraphicsUtil implements Icons {

    public static void drawJFrame(Graphics2D g, JFrame frame) {
        final int w = frame.getWidth();
        final int h = frame.getHeight();

        castAndSetupRHs(g);

        g.setColor(new Color(0x1acafa));
        g.fillRect(0, 0, w, h);

        if (frame.getIconImage() != null)
            g.drawImage(frame.getIconImage(), 7, 8, 16, 16, null);
        else
            JAVA.paintIcon(frame, g, 7, 8);

        g.setColor(Color.BLACK);

        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString(frame.getTitle(), 27, 13 + (g.getFontMetrics().getHeight() / 2));

        g.drawLine(w - 29, 10, w - 29 + 10, 10 + 10);
        g.drawLine(w - 29, 10 + 10, w - 29 + 10, 10);

        g.drawRect(w - 65, 10, 10, 10);

        g.drawLine(w - 101, 15, w - 91, 15);

        g.setColor(new Color(0xf0f0f0));
        g.fillRect(1, 31, w - 2, h - 32);
    }

    public static Graphics2D castAndSetupRHs(Graphics graphics) {
        final Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        return g;
    }

    public static Icon loadIcon(String path) {
        try {
            return new ImageIcon(ImageIO.read(Class.class.getResource(path)));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
