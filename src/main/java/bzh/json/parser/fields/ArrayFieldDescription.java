package bzh.json.parser.fields;

import bzh.json.parser.ObjectJsonMapper;
import bzh.json.parser.Helpers.Types;
import bzh.json.parser.exception.ObjectManipulateException;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayFieldDescription extends FieldDescription {
    List<FieldDescription> list = new ArrayList<>();

    public ArrayFieldDescription(Field field, Object obj, Types fieldFeature) throws ObjectManipulateException {
        super(field, obj, fieldFeature);
        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            list.add(ObjectJsonMapper.getFieldDescription(null, Array.get(obj, i)));
        }
    }

    @Override
    public String stringForJson() throws ObjectManipulateException {
        String fieldName = getFieldName();
        if (!fieldName.isEmpty()) {
            fieldName = String.format("\"%s\":", fieldName);
        }
        String json = fieldName + list.stream().map(it -> {
            return it.stringForJson();
        }).collect(Collectors.joining(",", "[", "]"));
        return json;
    }
}
