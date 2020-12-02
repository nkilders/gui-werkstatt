package de.noah.guiwerkstatt.property.properties;

import de.noah.guiwerkstatt.utility.MyEnumEntry;
import de.noah.guiwerkstatt.utility.Finals;

import java.awt.*;

public class CursorProperty extends AbstractMyEnumProperty<Container, Cursor> implements Finals {

    public CursorProperty(Container component) {
        super("Cursor", component, null, new MyEnumEntry[]{
                new MyEnumEntry("Default", CURSOR_DEFAULT),
                new MyEnumEntry("Hand", CURSOR_HAND),
                new MyEnumEntry("Move", CURSOR_MOVE),
                new MyEnumEntry("Wait", CURSOR_WAIT),
                new MyEnumEntry("Text", CURSOR_TEXT),
                new MyEnumEntry("Crosshair", CURSOR_CROSSHAIR),
                new MyEnumEntry("Resize N", CURSOR_RESIZE_N),
                new MyEnumEntry("Resize NE", CURSOR_RESIZE_NE),
                new MyEnumEntry("Resize E", CURSOR_RESIZE_E),
                new MyEnumEntry("Resize SE", CURSOR_RESIZE_SE),
                new MyEnumEntry("Resize S", CURSOR_RESIZE_S),
                new MyEnumEntry("Resize SW", CURSOR_RESIZE_SW),
                new MyEnumEntry("Resize W", CURSOR_RESIZE_W),
                new MyEnumEntry("Resize NW", CURSOR_RESIZE_NW)
        });
    }

    @Override
    public Cursor getValue() {
        return component.getCursor();
    }

    @Override
    protected void setVal(Cursor value) {
        component.setCursor(value);
    }

    @Override
    public String getCodeLine() {
        String name = "";
        switch (getValue().getType()) {
            case Cursor.DEFAULT_CURSOR:
                name = "DEFAULT_CURSOR";
                break;
            case Cursor.HAND_CURSOR:
                name = "HAND_CURSOR";
                break;
            case Cursor.MOVE_CURSOR:
                name = "MOVE_CURSOR";
                break;
            case Cursor.WAIT_CURSOR:
                name = "WAIT_CURSOR";
                break;
            case Cursor.TEXT_CURSOR:
                name = "TEXT_CURSOR";
                break;
            case Cursor.CROSSHAIR_CURSOR:
                name = "CROSSHAIR_CURSOR";
                break;
            case Cursor.N_RESIZE_CURSOR:
                name ="N_RESIZE_CURSOR";
                break;
            case Cursor.NE_RESIZE_CURSOR:
                name ="NE_RESIZE_CURSOR";
                break;
            case Cursor.E_RESIZE_CURSOR:
                name ="E_RESIZE_CURSOR";
                break;
            case Cursor.SE_RESIZE_CURSOR:
                name ="SE_RESIZE_CURSOR";
                break;
            case Cursor.S_RESIZE_CURSOR:
                name ="S_RESIZE_CURSOR";
                break;
            case Cursor.SW_RESIZE_CURSOR:
                name ="SW_RESIZE_CURSOR";
                break;
            case Cursor.W_RESIZE_CURSOR:
                name ="W_RESIZE_CURSOR";
                break;
            case Cursor.NW_RESIZE_CURSOR:
                name ="NW_RESIZE_CURSOR";
                break;
        }

        return String.format(".setCursor(new Cursor(Cursor.%s));", name);
    }
}
