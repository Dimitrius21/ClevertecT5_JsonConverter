package bzh.json.parser.Helpers;

import java.time.LocalDate;
import java.time.LocalTime;

public class Constants {
    public static final Class[] NUMBER_CLASSES = {Byte.class, byte.class, Short.class, short.class, Integer.class, int.class,
            Long.class, long.class, Float.class, float.class, Double.class, double.class};
    public static final Class[] LOGICAL_CLASSES = {Boolean.class, boolean.class};
    public static final Class[] CHARSEQUENCE_CLASSES = { Character.class, char.class, String.class, LocalDate.class, LocalTime.class};
}
