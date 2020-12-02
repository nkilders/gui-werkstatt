package de.noah.guiwerkstatt.property.properties;

import de.noah.guiwerkstatt.utility.MyEnumEntry;

import javax.swing.*;

public class AlignmentProperty extends AbstractMyEnumProperty<AbstractButton, Integer> {
    private final boolean horizontal;

    public AlignmentProperty(AbstractButton component, boolean horizontal) {
        super((horizontal ? "H" : "V") + " Alignement", component, null, horizontal ? new MyEnumEntry[]{
                new MyEnumEntry("Left", 2),
                new MyEnumEntry("Center", 0),
                new MyEnumEntry("Right", 4),
                new MyEnumEntry("Leading", 10),
                new MyEnumEntry("Trailing", 11)
        } : new MyEnumEntry[]{
                new MyEnumEntry("Top", 1),
                new MyEnumEntry("Center", 0),
                new MyEnumEntry("Bottom", 3)
        });

        this.horizontal = horizontal;
    }

    @Override
    public Integer getValue() {
        return horizontal ? component.getHorizontalAlignment() : component.getVerticalAlignment();
    }

    @Override
    protected void setVal(Integer value) {
        if (horizontal) {
            component.setHorizontalAlignment(value);
        } else {
            component.setVerticalAlignment(value);
        }
    }

    @Override
    public String getCodeLine() {
        return String.format(".set%sAlignment(%s);", horizontal ? "Horizontal" : "Vertical", getValue().toString());
    }
}
