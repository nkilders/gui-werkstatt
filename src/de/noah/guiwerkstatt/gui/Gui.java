package de.noah.guiwerkstatt.gui;

import de.noah.guiwerkstatt.GuiWerkstatt;
import de.noah.guiwerkstatt.gui.components.EditorPanel;
import de.noah.guiwerkstatt.gui.components.OutlinePanel;
import de.noah.guiwerkstatt.gui.components.PropertiesPanel;
import de.noah.guiwerkstatt.gui.components.ToolboxPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Gui extends JFrame {
    private final GuiWerkstatt guiWerkstatt;

    private JMenuBar menuBar;
    private Container content;

    private JTabbedPane leftBar;
    private OutlinePanel outline;
    private ToolboxPanel toolbox;
    private PropertiesPanel properties;
    private EditorPanel editor;

    public Gui(GuiWerkstatt guiWerkstatt) {
        this.guiWerkstatt = guiWerkstatt;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        setTitle(GuiWerkstatt.NAME);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                leftBar.setBounds(0, 0, 250, getHeight() - 59);
                editor.setBounds(250, 0, getWidth() - 250 - 16, getHeight() - 59);
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

            }
        });

        setJMenuBar(menuBar = new JMenuBar());
        {
            JMenu menu;
            JMenuItem item;

            menuBar.add(menu = new JMenu("Code"));

            menu.add(item = new JMenuItem("Code exportieren"));
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));

            menu.add(item = new JMenuItem("Version: " + GuiWerkstatt.VERSION));
            item.setEnabled(false);
        }

        content = getContentPane();
        content.setLayout(null);

        content.add(leftBar = new JTabbedPane(JTabbedPane.BOTTOM, JTabbedPane.SCROLL_TAB_LAYOUT));
        leftBar.setBounds(0, 0, 250, getHeight() - 59);
        leftBar.setFocusable(false);

        leftBar.addTab("Outline", outline = new OutlinePanel(this));
        leftBar.addTab("Toolbox", toolbox = new ToolboxPanel(this));
        leftBar.addTab("Properties", properties = new PropertiesPanel(this));

        content.add(editor = new EditorPanel(this));
        editor.setBounds(250, 0, getWidth() - 250 - 16, getHeight() - 59);
    }

    public OutlinePanel getOutline() {
        return outline;
    }

    public ToolboxPanel getToolbox() {
        return toolbox;
    }

    public PropertiesPanel getProperties() {
        return properties;
    }

    public EditorPanel getEditor() {
        return editor;
    }
}