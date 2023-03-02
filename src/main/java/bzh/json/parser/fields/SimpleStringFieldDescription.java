package bzh.json.parser.fields;

import bzh.json.parser.Helpers.Types;
import bzh.json.parser.exception.ObjectManipulateException;

import java.lang.reflect.Field;

public class SimpleStringFieldDescription extends FieldDescription {
    public SimpleStringFieldDescription(Field field, Object obj, Types fieldFeature) {
        super(field, obj, fieldFeature);
    }

    @Override
    public String stringForJson() throws ObjectManipulateException {
        String fieldName = getFieldName();
        String res = String.format("\"%s\"",getFieldValue());
        if (!fieldName.isEmpty()) { res = String.format("\"%s\":%s",fieldName,res); }
        return res;
    }
}
