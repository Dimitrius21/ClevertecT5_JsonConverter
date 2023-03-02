package bzh.json.parser.Helpers;

import bzh.json.parser.exception.JsonConvertException;
import bzh.json.parser.exception.ObjectManipulateException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static bzh.json.parser.ObjectJsonMapper.*;

public class ParseUtil {
    public static Object parseArray(String json, Class clazz) throws JsonConvertException, ObjectManipulateException {
        Pattern p = Pattern.compile("^\\[\\s*(.+)\\s*]$");
        Matcher m = p.matcher(json);
        if (!m.matches()) { throw new JsonConvertException(); }
        String val = m.group(1).trim();
        Class component = clazz.getComponentType();
        String classType = component.getTypeName().toUpperCase().replace('.', '_');
        if (SimpleClasses.isPureString(classType)) {
            return getResultObject(str -> Arrays.asList(str.split(",")), val, component);
        }
        if (SimpleClasses.isMarkString(classType)) {
            return getResultObject(ObjectJsontUtil::getListOfString, val, component);
        }
        String objectType = "{}";
        if (component.isArray()) {
            objectType = "[]";
        }
        return getResultObject(ObjectJsontUtil::getListObject, objectType + val, component);
    }

    public static Object parseCollection(String json, Class clazz) throws JsonConvertException, ObjectManipulateException {
        Collection object = (Collection) ObjectJsontUtil.createObject(clazz);
        Pattern p = Pattern.compile("^\\[\\s*(.+)\\s*]$");
        Matcher m = p.matcher(json);
        if (!m.matches()) { throw new JsonConvertException(); }
        String val = m.group(1).trim();
        char first = val.charAt(0);
        if (first == '"') {
            ObjectJsontUtil.getListOfString(val).forEach(it -> {
                object.add(toObject(it, String.class));
            });
        } else
        if (first == '{' || first == '[') {
            String objectType = "{}";
            if (first == '[') { objectType = "[]";}
            ObjectJsontUtil.getListObject(objectType + val).forEach(it -> object.add(it));
        } else {
            Arrays.stream(val.split("\\s*,\\s*")).forEach(it -> object.add(it.trim()));
        }
        return object;
    }

    public static Object parseMap(String json, Class clazz) throws JsonConvertException, ObjectManipulateException {
        Map object = (Map) ObjectJsontUtil.createObject(clazz);
        Pattern p = Pattern.compile("^\\{\\s*(.+)\\s*}$");
        Matcher m = p.matcher(json);
        if (!m.matches()) { throw new JsonConvertException(); }
        String val = m.group(1).trim();
        return ObjectJsontUtil.getParseMap(val, object);
    }

    private static Object getResultObject(Function<String, List<String>> separator, String json, Class component) throws JsonConvertException, ObjectManipulateException {
        List<String> items = separator.apply(json);
        Object result = Array.newInstance(component, items.size());
        for (int i = 0; i < items.size(); i++) {
            Array.set(result, i, toObject(items.get(i), component));
        }
        return result;
    }
}
