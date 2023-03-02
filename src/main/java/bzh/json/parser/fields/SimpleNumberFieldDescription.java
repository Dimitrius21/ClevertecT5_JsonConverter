package bzh.json.parser.fields;

import bzh.json.parser.Helpers.Types;
import bzh.json.parser.exception.ObjectManipulateException;

import java.lang.reflect.Field;

public class SimpleNumberFieldDescription extends FieldDescription {
    public SimpleNumberFieldDescription(Field field, Object obj, Types fieldFeature) {
        super(field, obj, fieldFeature);
    }

    @Override
    public String stringForJson() throws ObjectManipulateException {
        String fieldName = getFieldName();
        String res = getFieldValue().toString();
        if (!fieldName.isEmpty()) { res = String.format("\"%s\":%s",fieldName,res); }
        return res;
    }
}
