package de.noah.guiwerkstatt.utility;

import java.awt.*;

public interface Finals {
    BasicStroke STROKE_DEFAULT = new BasicStroke(1.0F);
    BasicStroke STROKE_DASHED = new BasicStroke(1.0F, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0F, new float[]{2.0F}, 1.0F);

    Cursor CURSOR_DEFAULT = new Cursor(Cursor.DEFAULT_CURSOR);
    Cursor CURSOR_HAND = new Cursor(Cursor.HAND_CURSOR);
    Cursor CURSOR_MOVE = new Cursor(Cursor.MOVE_CURSOR);
    Cursor CURSOR_WAIT = new Cursor(Cursor.WAIT_CURSOR);
    Cursor CURSOR_TEXT = new Cursor(Cursor.TEXT_CURSOR);
    Cursor CURSOR_CROSSHAIR = new Cursor(Cursor.CROSSHAIR_CURSOR);
    Cursor CURSOR_RESIZE_N = new Cursor(Cursor.N_RESIZE_CURSOR);
    Cursor CURSOR_RESIZE_NE = new Cursor(Cursor.NE_RESIZE_CURSOR);
    Cursor CURSOR_RESIZE_E = new Cursor(Cursor.E_RESIZE_CURSOR);
    Cursor CURSOR_RESIZE_SE = new Cursor(Cursor.SE_RESIZE_CURSOR);
    Cursor CURSOR_RESIZE_S = new Cursor(Cursor.S_RESIZE_CURSOR);
    Cursor CURSOR_RESIZE_SW = new Cursor(Cursor.SW_RESIZE_CURSOR);
    Cursor CURSOR_RESIZE_W = new Cursor(Cursor.W_RESIZE_CURSOR);
    Cursor CURSOR_RESIZE_NW = new Cursor(Cursor.NW_RESIZE_CURSOR);

    char[] ALPHABET = new char[]{
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'a', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };
    char[] NUMBERS = new char[]{
            '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'
    };
}