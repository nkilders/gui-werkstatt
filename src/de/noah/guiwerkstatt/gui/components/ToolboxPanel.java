package de.noah.guiwerkstatt.gui.components;

import de.noah.guiwerkstatt.gui.Gui;
import de.noah.guiwerkstatt.utility.GraphicsUtil;
import de.noah.guiwerkstatt.utility.Icons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class ToolboxPanel extends ScrollPanel {
    private final Gui gui;

    private List<ToolButton> tools = new ArrayList<>();
    private ToolButton selected = null;

    public ToolboxPanel(Gui gui) {
        this.gui = gui;

        {
            addTool(JButton.class);
            addTool(JCheckBox.class);
            addTool(JLabel.class);
            addTool(JPanel.class);
            addTool(JPasswordField.class);
            addTool(JProgressBar.class);
            // TODO: ButtonGroup
            // addTool(JRadioButton.class);
            addTool(JSeparator.class);
            addTool(JSlider.class);
            // addTool(JSpinner.class);
            // addTool(JSplitPane.class);
            // addTool(JTabbedPane.class);
            addTool(JTextArea.class);
            addTool(JTextField.class);
            // addTool(JTextPane.class);
            // addTool(JTree.class);
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Component c = getComponentAt(e.getPoint());
                if (c instanceof ToolButton) {
                    selected = (ToolButton) c;
                } else {
                    selected = null;
                }

                repaint();
            }
        });
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);

        for (ToolButton tb : tools) {
            tb.setSize(width, 20);
        }
    }

    public void addTool(Class<? extends JComponent> tool) {
        final ToolButton tb = new ToolButton(tool);
        tb.setBounds(0, tools.size() * 20, getWidth(), 20);

        tools.add(tb);
        add(tb);
    }

    public class ToolButton extends JComponent {
        private final Class<? extends JComponent> tool;
        private final Icon icon;

        ToolButton(Class<? extends JComponent> tool) {
            this.tool = tool;
            this.icon = Icons.getFromClass(tool);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            final Graphics2D g = GraphicsUtil.castAndSetupRHs(graphics);

            if (selected == this) {
                g.setColor(new Color(0x3399ff));
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(Color.BLACK);
            }

            icon.paintIcon(this, graphics, 2, (getHeight() / 2) - (icon.getIconHeight() / 2));
            g.drawString(tool.getSimpleName(), 20, (getHeight() / 2) + (g.getFontMetrics().getHeight() / 3));
        }

        public Class<? extends JComponent> getTool() {
            return tool;
        }
    }

    public ToolButton getSelected() {
        return selected;
    }

    public void setSelected(ToolButton selected) {
        this.selected = selected;
        repaint();
    }
}