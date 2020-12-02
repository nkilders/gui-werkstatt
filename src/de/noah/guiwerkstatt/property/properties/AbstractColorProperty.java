package de.noah.guiwerkstatt.property.properties;

import de.noah.guiwerkstatt.gui.components.ColorDisplay;
import de.noah.guiwerkstatt.property.AbstractProperty;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class AbstractColorProperty<$COMP extends Container> extends AbstractProperty<$COMP, Color> {
    private final ColorDisplay editor;

    public AbstractColorProperty(String name, $COMP component, AbstractProperty parent) {
        super(name, component, parent);

        this.editor = new ColorDisplay();
        this.editor.setColor(getValue());
        this.editor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("xx");
                JColorChooser jcc = new JColorChooser();
                jcc.setColor(editor.getColor());
                JDialog d = JColorChooser.createDialog(editor,
                        "Farbe auswählen",
                        true,
                        jcc,
                        f -> {
                            setValue(jcc.getColor());
                        },
                        null);
                d.setVisible(true);
            }
        });
    }

    @Override
    public void setValue(Color value) {
        super.setValue(value);
        editor.setColor(value);
    }

    @Override
    public ColorDisplay getEditor() {
        return editor;
    }
}