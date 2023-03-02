package bzh.json.parser;

import bzh.json.parser.Helpers.*;
import bzh.json.parser.exception.JsonConvertException;
import bzh.json.parser.exception.ObjectManipulateException;
import bzh.json.parser.fields.*;

import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ObjectJsonMapper {

    public static Object toObject(String json, Class clazz) throws JsonConvertException, ObjectManipulateException  {
        if (clazz.isArray()) {
            return ParseUtil.parseArray(json, clazz);
        }
        if (ObjectJsontUtil.isClassImplementInterface(clazz, Collection.class)) {
            return ParseUtil.parseCollection(json, clazz);
        }
        if (ObjectJsontUtil.isClassImplementInterface(clazz, Map.class)) {
            return ParseUtil.parseMap(json, clazz);
        }

        try {
            return SimpleClasses.valueOf(clazz.getName().toUpperCase().replace('.', '_')).getValue(json);
        } catch (NumberFormatException ex) {
            throw new JsonConvertException();
        } catch (IllegalArgumentException ex) {
        }
        List<Field> fields = ObjectJsontUtil.getFieldsList(clazz);
        String pattern = fields.stream().map(field -> "\"" + field.getName() + "\"" + "\\s*:\\s*(.+)")
                .collect(Collectors.joining("\\s*,\\s*", "^\\{\\s*", "\\}$"));
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(json);
        Object object = null;
        if (m.matches()) {
            object = ObjectJsontUtil.createObject(clazz);
            for (int i = 0; i < fields.size(); i++) {
                Field field = fields.get(i);
                field.setAccessible(true);
                try {
                    field.set(object, toObject(m.group(i + 1), field.getType()));
                } catch (IllegalAccessException e) {
                    throw new ObjectManipulateException("Can't access to field" + field.getName(), e);
                }
            }
        }
        return object;
    }

    public static String toJson(Object obj)  throws JsonConvertException, ObjectManipulateException{
        return getFieldDescription(null, obj).stringForJson();
    }

    public static FieldDescription getFieldDescription(Field field, Object obj) throws ObjectManipulateException {
        if (SimpleClasses.isPureString(obj.getClass().getName().toUpperCase().replace('.', '_'))){  //((obj.getClass().isPrimitive() && obj.getClass()!=char.class) || obj instanceof Number){
            return new SimpleNumberFieldDescription(field, obj, Types.NUMBER);
        } else if (Arrays.asList(Constants.CHARSEQUENCE_CLASSES).contains(obj.getClass())) {
            return new SimpleStringFieldDescription(field, obj, Types.CHARSEQUENCE);
        }
        if (obj.getClass().isArray()) {
            return new ArrayFieldDescription(field, obj,Types.ARRAY);
        }
        if (obj instanceof Collection<?>) {
            return new CollectionFieldDescription(field, obj, Types.COLLECTION);
        }
        if (obj instanceof Map<?, ?>) {
            return new MapFieldDescription(field, obj, Types.MAP);
        }
        return new ObjectFieldDescription(field, obj, Types.OBJECT);
    }
}
