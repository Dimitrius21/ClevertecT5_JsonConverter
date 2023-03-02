package bzh.json.parser.fields;

import bzh.json.parser.ObjectJsonMapper;
import bzh.json.parser.Helpers.Types;
import bzh.json.parser.exception.ObjectManipulateException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionFieldDescription extends FieldDescription {
    List<FieldDescription> list = new ArrayList<>();

    public CollectionFieldDescription(Field field, Object obj, Types fieldFeature) throws ObjectManipulateException {
        super(field, obj, fieldFeature);
            List items = new ArrayList<>((Collection<?>) obj);
            for (Object item : items){
                list.add(ObjectJsonMapper.getFieldDescription(null, item));
            }
    }

    @Override
    public String stringForJson() throws ObjectManipulateException {
        String fieldName = getFieldName();
        if (!fieldName.isEmpty()) {
            fieldName = String.format("\"%s\":", fieldName);
        }
        String json = fieldName + list.stream().map(it -> it.stringForJson())
                .collect(Collectors.joining(",", "[", "]"));
        return json;
    }
}
