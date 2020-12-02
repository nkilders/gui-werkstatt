package de.noah.guiwerkstatt.utility;

public class MyEnumEntry<$VAL> {
    public final String key;
    public final $VAL value;

    public MyEnumEntry(String key, $VAL value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return key;
    }
}