package de.noah.guiwerkstatt.code;

import de.noah.guiwerkstatt.gui.components.AComponent;
import de.noah.guiwerkstatt.property.AbstractProperty;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class CodeGenerator {
    private static StringBuilder sb = null;
    private static int numTabs = 0;
    private static String tabs = "";

    public static boolean saveToFile(AComponent frame, File file) {
        final String code = generate(frame);

        try {
            PrintWriter writer = new PrintWriter(new FileWriter(file));
            writer.print(code);
            writer.close();

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public static synchronized String generate(AComponent frame) {
        sb = new StringBuilder();
        numTabs = 0;
        tabs = "";

        // Imports
        append("import javax.swing.*;");
        append("import java.awt.*;");
        append("");

        // Class Header
        append("public class MeinGui {");
        addTabs(1);

        // Variablen definieren
        new Object() {
            private void v(AComponent comp) {
                append("private %s %s;", comp.getComponent().getClass().getSimpleName(), comp.getVarName());

                for (Component c : comp.getComponents()) {
                    if (c instanceof AComponent) {
                        v((AComponent) c);
                    }
                }
            }
        }.v(frame);
        append("");

        // Method Header
        append("public void erzeugeGui() {");
        addTabs(1);

        new Object() {
            private void v(final AComponent comp, final AComponent parent) {
                final Container c = comp.getComponent();

                // Objekt erzeugen
                append("%s = new %s();", comp.getVarName(), c.getClass().getSimpleName());

                if (parent != null ? (parent.getComponent() instanceof Window) : false) {
                    // Zeugs für content pane
                    append("%s.setLayout(null);", comp.getVarName());
                    append("%s.setContentPane(%s);", parent.getVarName(), comp.getVarName());
                } else {
                    // Attribute setzen
                    for (AbstractProperty p : comp.getProperties().getAllProperties()) {
                        if (p.isEdited() && !p.getName().equals("Var Name")) {
                            append(comp.getVarName() + p.getCodeLine());
                        }
                    }

                    // setLocRelTo null bei JFrame
                    if (c instanceof Window) {
                        append("%s.setLocationRelativeTo(null);", comp.getVarName());
                    }

                    // zu parent hinzufügen
                    if (parent != null) {
                        append("%s.add(%s);", parent.getVarName(), comp.getVarName());
                    }
                }

                append("");

                // für children wiederholen
                for (Component child : comp.getComponents()) {
                    if (child instanceof AComponent) {
                        v((AComponent) child, comp);
                    }
                }
            }
        }.v(frame, null);

        // Frame sichtbar machen
        append("%s.setVisible(true);", frame.getVarName());

        // Method Footer
        addTabs(-1);
        append("}");

        // Class Footer
        addTabs(-1);
        append("}");

        return sb.toString();
    }

    private static void addTabs(int number) {
        if (number != 0) {
            numTabs += number;
            tabs = "";

            for (int i = 0; i < numTabs; i++) {
                tabs = "    ".concat(tabs);
            }
        }
    }

    private static void append(String s) {
        sb.append(tabs + s + "\n");
    }

    private static void append(String s, Object... o) {
        append(String.format(s, o));
    }
}