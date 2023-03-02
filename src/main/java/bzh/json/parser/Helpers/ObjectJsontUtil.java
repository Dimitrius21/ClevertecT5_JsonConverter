package bzh.json.parser.Helpers;


import bzh.json.parser.exception.JsonConvertException;
import bzh.json.parser.exception.ObjectManipulateException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ObjectJsontUtil {
    public static boolean isClassImplementInterface(Class clazz, Class interfaceClass) {
        if (clazz.isPrimitive()) return false;
        do {
            Class[] interfaces = clazz.getInterfaces();
            boolean hasMapInterface= Arrays.stream(interfaces).anyMatch(it->interfaceClass==it);
            if (hasMapInterface) return true;
            else clazz = clazz.getSuperclass();
        } while (clazz != Object.class);
        return false;
    }

    public static List<String> getListOfString(String json) throws JsonConvertException {
        List<String> list = new ArrayList<>();
        int pos = 0;
        if (json.startsWith("\"") && json.endsWith("\"")) {
            while (true) {
                pos = spaceSkip(json, pos);
                String subString = getFirstString(json, pos);
                if (subString == null) { throw new JsonConvertException(); }
                pos = pos + subString.length();
                list.add(subString);
                if (pos>=json.length()) {return list;}
                pos = spaceSkip(json, pos);
                char c=json.charAt(pos);
                if (json.charAt(pos) != ',') {
                    throw new JsonConvertException();
                }
                pos++;
            }
        }
        throw new JsonConvertException();
    }

    private static int spaceSkip(String s, int i) {
        char symbol = s.charAt(i);
        while (String.valueOf(symbol).matches("\\s")){
            symbol = s.charAt(++i);
        }
        return i;
    }

    public static String getFirstString(String json, int pos) {
        StringBuilder sb = new StringBuilder();
        boolean startItem = true;
        if (json.charAt(pos) != '"') {
            return null;
        }
        while (pos < json.length()) {
            sb.append(json.charAt(pos));
            if (json.charAt(pos) == '"') {
                if (startItem) {
                    startItem = false;
                } else if (json.charAt(pos - 1) != '\\') {
                    return sb.toString();
                }
            }
            pos++;
        }
        return null;
    }

    public static Map getParseMap(String json, Map map) throws JsonConvertException {
        int pos = 0;
        while (pos < json.length()) {
            pos = spaceSkip(json, pos);
            String key = getFirstString(json, pos);
            if (key == null) { throw new JsonConvertException(); }
            pos = pos + key.length();
            key = key.substring(1, key.length() - 1);
            pos = spaceSkip(json, pos);
            if (json.charAt(pos) != ':') { throw new JsonConvertException(); }
            pos++;
            pos = spaceSkip(json, pos);
            String val = getFirstString(json, pos);
            if (val == null) {
                StringBuilder sb = new StringBuilder();
                while (json.charAt(pos) != ',' || !String.valueOf(json.charAt(pos)).matches("\\s") || pos <= json.length()) {
                    sb.append(json.charAt(pos));
                    pos++;
                }
                val = sb.toString().trim();
            } else {
                pos = pos + val.length();
                val = val.substring(1, val.length() - 1);
            }
            map.put(key, val);
            if (pos < json.length()) {
                pos = spaceSkip(json, pos);
                if (json.charAt(pos) != ',') { throw new JsonConvertException(); }
                pos++;
            }
        }
        return map;
    }
    public static List<String> getListObject(String json) {
        List<String> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        char typeStart = json.charAt(0);
        char typeEnd = json.charAt(1);
        int i = 2;
        int level = 0;
        if (json.charAt(0) == typeStart && json.charAt(json.length() - 1) == typeEnd) {
            while (true) {
                sb.append(json.charAt(i));
                if (json.charAt(i) == typeStart) {
                    level++;
                } else if (json.charAt(i) == typeEnd) {
                    level--;
                    if (level == 0) ;
                    list.add(sb.toString());
                    if (i == json.length() - 1) {
                        break;
                    }
                    sb = new StringBuilder();
                    i++;
                    i = spaceSkip(json, i);
                    if (json.charAt(i) != ',') {
                        throw new JsonConvertException();
                    }
                    i++;
                    i = spaceSkip(json, i);
                    i--;
                }
                i++;
            }
        } else throw new JsonConvertException();
        if (level == 0) return list;
        else throw new JsonConvertException();
    }

    public static List<Field> getFieldsList(Class clazz) {
        System.out.println(clazz.getName());
        List<Field> fieldList = new ArrayList<>();
        fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
        Class superclass = clazz.getSuperclass();
        if (superclass != Object.class) {
            fieldList.addAll(getFieldsList(superclass));
        }
        return fieldList;
    }
    public static Object createObject(Class clazz) throws ObjectManipulateException {
        try {
            return Arrays.stream(clazz.getDeclaredConstructors()).filter(c -> c.getParameterCount() == 0).findFirst()
                    .get().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new ObjectManipulateException("Could not create object of " + clazz.getName());
        }
    }
}
