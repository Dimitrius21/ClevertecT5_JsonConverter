package bzh.json.parser.fields;

import bzh.json.parser.Helpers.Types;
import bzh.json.parser.exception.ObjectManipulateException;

import java.lang.reflect.Field;

public abstract class FieldDescription {
    private Field field;
    private Object obj;
    private Types fieldFeature;

    public FieldDescription(Field field, Object obj, Types fieldFeature) {
        this.field = field;
        this.obj = obj;
        this.fieldFeature = fieldFeature;
    }

    public FieldDescription() {
    }

    public Field getField() {
        return field;
    }

    public String getFieldName(){
        if (field==null) return "";
        return field.getName();
    }
    public Object getFieldValue() throws ObjectManipulateException {
        if (field==null || fieldFeature==Types.NUMBER || fieldFeature==Types.CHARSEQUENCE) return obj;
        field.setAccessible(true);
        try {
            return field.get(obj);
        } catch (IllegalAccessException e) {
            throw new ObjectManipulateException("Can't get access to object "+ obj.getClass());
        }
    }
    public Types getFieldFeature() {
        return fieldFeature;
    }


    public abstract String stringForJson() throws ObjectManipulateException;

}
