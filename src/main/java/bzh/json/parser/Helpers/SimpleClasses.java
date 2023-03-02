package bzh.json.parser.Helpers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.function.Function;

public enum SimpleClasses {
    JAVA_LANG_BYTE(Byte::valueOf, Constants.NUMBER_CLASSES[0]),
    BYTE(Byte::valueOf, Constants.NUMBER_CLASSES[1]),
    JAVA_LANG_SHORT(Short::valueOf, Constants.NUMBER_CLASSES[2]),
    SHORT(Short::valueOf, Constants.NUMBER_CLASSES[3]),
    JAVA_LANG_INTEGER(Integer::valueOf, Constants.NUMBER_CLASSES[4]),
    INT(v->(int)Integer.valueOf(v), Constants.NUMBER_CLASSES[5]),
    JAVA_LANG_LONG(Long::valueOf, Constants.NUMBER_CLASSES[6]),
    LONG(Long::valueOf, Constants.NUMBER_CLASSES[7]),
    JAVA_LANG_FLOAT(Float::valueOf, Constants.NUMBER_CLASSES[8]),
    FLOAT(Float::valueOf, Constants.NUMBER_CLASSES[9]),
    JAVA_LANG_DOUBLE(Double::valueOf, Constants.NUMBER_CLASSES[10]),
    DOUBLE(Double::valueOf, Constants.NUMBER_CLASSES[11]),

    JAVA_LANG_BOOLEAN(Boolean::valueOf, Constants.LOGICAL_CLASSES[0]),
    BOOLEAN(Boolean::valueOf, Constants.LOGICAL_CLASSES[1]),

    JAVA_LANG_CHARACTER(val -> {
        if (val.matches("\".*\"")) {
            return val.substring(1, val.length() - 1);
        } else {
            throw new NumberFormatException();
        }
    }, Constants.CHARSEQUENCE_CLASSES[0]),
    CHAR(val -> {
        if (val.matches("\".*\"")) {
            return val.substring(1, val.length() - 1);
        } else {
            throw new NumberFormatException();
        }
    }, Constants.CHARSEQUENCE_CLASSES[1]),
    JAVA_LANG_STRING(val -> {
        if (val.matches("\".*\"")) {
            return val.substring(1, val.length() - 1);
        } else {
            throw new NumberFormatException();
        }
    }, Constants.CHARSEQUENCE_CLASSES[2]),
    JAVA_TIME_LOCALDATE(val -> {
        if (val.matches("\".*\"")) {
            return LocalDate.parse(val.substring(1, val.length() - 1));
        } else {
            throw new NumberFormatException();
        }
    }, Constants.CHARSEQUENCE_CLASSES[3]),
    JAVA_TIME_LOCALTIME(val -> {
        if (val.matches("\".*\"")) {
            return LocalTime.parse(val.substring(1, val.length() - 1));
        } else {
            throw new NumberFormatException();
        }
    }, Constants.CHARSEQUENCE_CLASSES[4])
    ;
    private Function<String, Object> function;
    private Class clazz;

    SimpleClasses(Function<String, Object> function, Class clazz) {
        this.function = function;
        this.clazz = clazz;
    }

    public Object getValue(String val) {
        return function.apply(val);
    }

    public Class getClassForField() {
        return clazz;
    }

    public static boolean isPureString(String val) {
        try {
            SimpleClasses item = SimpleClasses.valueOf(val);
            if (Arrays.asList(Constants.NUMBER_CLASSES).contains(item.getClassForField())
                    || Arrays.asList(Constants.LOGICAL_CLASSES).contains(item.getClassForField())){
                return true;
            }
        } catch (IllegalArgumentException ex) {}
        return false;
    }
    public static boolean isMarkString(String val) {
        try {
            SimpleClasses item = SimpleClasses.valueOf(val);
            if (Arrays.asList(Constants.CHARSEQUENCE_CLASSES).contains(item.getClassForField())){
                return true;
            }
        } catch (IllegalArgumentException ex) {}
        return false;
    }
}

