package de.noah.guiwerkstatt;

import de.noah.guiwerkstatt.gui.Gui;

public class GuiWerkstatt {
    public static final String NAME = "Gui Werkstatt";
    public static final String VERSION = "R180820a";

    private static GuiWerkstatt INSTANCE;

    private Gui gui;

    public GuiWerkstatt() {
        INSTANCE = this;

        gui = new Gui(this);
        gui.setVisible(true);
    }

    public static GuiWerkstatt getInstance() {
        return INSTANCE;
    }

    public Gui getGui() {
        return gui;
    }
}