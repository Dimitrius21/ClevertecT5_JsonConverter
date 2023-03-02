package bzh.json.parser.fields;

import bzh.json.parser.ObjectJsonMapper;
import bzh.json.parser.Helpers.Types;
import bzh.json.parser.exception.ObjectManipulateException;

import java.lang.reflect.Field;
import java.util.*;

public class MapFieldDescription extends FieldDescription {
    Map<FieldDescription, FieldDescription> map = new HashMap<>();


    public MapFieldDescription(Field field, Object obj, Types fieldFeature) throws ObjectManipulateException {
        super(field, obj, fieldFeature);
        ((Map)obj).forEach((k,v)->{
            map.put(ObjectJsonMapper.getFieldDescription(null, k), ObjectJsonMapper.getFieldDescription(null, v));
        });
    }

    @Override
    public String stringForJson() throws ObjectManipulateException {
        String fieldName = getFieldName();
        if (!fieldName.isEmpty()) {
            fieldName = String.format("\"%s\":", fieldName);
        }
        StringBuilder sb = new StringBuilder(fieldName).append('{');
        map.forEach((k,v) -> {
            if (k.getFieldFeature()== Types.NUMBER){
                sb.append("\"").append(k.stringForJson()).append("\"");
            } else {sb.append(k.stringForJson());}
            sb.append(":").append(v.stringForJson()).append(",");
        });
        return sb.deleteCharAt(sb.length()-1).append('}').toString();
    }
}
