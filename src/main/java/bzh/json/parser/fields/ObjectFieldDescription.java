package bzh.json.parser.fields;

import bzh.json.parser.ObjectJsonMapper;
import bzh.json.parser.Helpers.ObjectJsontUtil;
import bzh.json.parser.Helpers.Types;
import bzh.json.parser.exception.ObjectManipulateException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ObjectFieldDescription extends FieldDescription {
    List<FieldDescription> list = new ArrayList<>();

    public ObjectFieldDescription(Field field, Object obj, Types fieldFeature) {
        super(field, obj, fieldFeature);
        List<Field> fieldList = ObjectJsontUtil.getFieldsList(obj.getClass());
        fieldList.stream().forEach(f -> {
            try {
                f.setAccessible(true);
                list.add(ObjectJsonMapper.getFieldDescription(f, f.get(obj)));
            } catch (IllegalAccessException e) {
                throw new ObjectManipulateException("Can't get access to object" + obj.getClass());
            }
        });
    }

    @Override
    public String stringForJson()  {
        String fieldName = getFieldName();
        if (!fieldName.isEmpty()) {
            fieldName = String.format("\"%s\":", fieldName);
        }
        String json = fieldName + list.stream().map(it -> {
            return it.stringForJson();
        }).collect(Collectors.joining(",", "{", "}"));
        return json;
    }
}
