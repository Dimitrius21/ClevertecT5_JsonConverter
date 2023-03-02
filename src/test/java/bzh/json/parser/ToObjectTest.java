package bzh.json.parser;

import bzh.json.parser.ObjectJsonMapper;
import bzh.json.parser.exception.JsonConvertException;
import bzh.json.parser.exception.ObjectManipulateException;
import bzh.json.parser.testObject.Component;
import bzh.json.parser.testObject.Item;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class ToObjectTest {
    @Test
    void parseSimpleSting() throws JsonConvertException, ObjectManipulateException {
        String json = "\"testString\"";
        String expectedStr = "testString";
        String res = (String) ObjectJsonMapper.toObject(json, String.class);
        assertThat(res).isEqualTo(expectedStr);
    }
    @Test
    void parseSimpleInt() throws JsonConvertException, ObjectManipulateException {
        String json = "123456";
        int expectedInt = 123456;
        int res = (int) ObjectJsonMapper.toObject(json, int.class);
        assertThat(res).isEqualTo(expectedInt);
    }
    @Test
    void parseSimpleDouble() throws JsonConvertException, ObjectManipulateException {
        String json = "1234.56";
        double expectedDbl = 1234.56;
        double res = (double) ObjectJsonMapper.toObject(json, double.class);
        assertThat(res).isEqualTo(expectedDbl);
    }
    @Test
    void parseArrayOfString() throws JsonConvertException, ObjectManipulateException {
        String json = "[\"123\", \"456\"]";
        String[] expectedArr = {"123", "456"};
        String[] res = (String[]) ObjectJsonMapper.toObject(json, String[].class);
        assertThat(res).isEqualTo(expectedArr);
    }
    @Test
    void parseArrayOfComponent() throws JsonProcessingException, JsonConvertException, ObjectManipulateException {
        ObjectMapper mapper = new ObjectMapper();
        Component component1 = new Component("Comp1", 5);
        Component component2 = new Component("Comp2", 15);
        Component[] components = {component1, component2};
        String json = mapper.writeValueAsString(components);
        Component[] res = (Component[]) ObjectJsonMapper.toObject(json, Component[].class);
        assertThat(res).isEqualTo(components);
    }
    @Test
    void parseObject() throws JsonProcessingException, JsonConvertException, ObjectManipulateException {
        ObjectMapper mapper = new ObjectMapper();
        Component component = new Component("Comp1", 5);
        Item item = new Item("Name1", component);
        String json = mapper.writeValueAsString(item);
        Item res = (Item) ObjectJsonMapper.toObject(json, Item.class);
        assertThat(res).isEqualTo(item);
    }

    @Test
    void parseList() throws JsonProcessingException, JsonConvertException, ObjectManipulateException {
        ObjectMapper mapper = new ObjectMapper();
        List<String> lists = new ArrayList<>();
        lists.add("str1");
        lists.add("str2");
        String json = mapper.writeValueAsString(lists);
        List myRes = (ArrayList) ObjectJsonMapper.toObject(json, ArrayList.class);
        assertThat(myRes).isEqualTo(lists);
    }

    @Test
    void parseMap() throws JsonProcessingException, JsonConvertException, ObjectManipulateException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = new HashMap<>();
        map.put("key1", "val1");
        map.put("key2", "val2");
        String json = mapper.writeValueAsString(map);
        Map myRes = (Map) ObjectJsonMapper.toObject(json, HashMap.class);
        assertThat(myRes).isEqualTo(map);
    }

/*    @Test
    void parseListOfComponent() throws JsonProcessingException, InvocationTargetException, InstantiationException, JsonConvertException, ObjectManipulateException {
        ObjectMapper mapper = new ObjectMapper();
        Component component1 = new Component("Comp1", 5);
        Component component2 = new Component("Comp2", 15);
        List<Component> lists = new ArrayList<>();
        lists.add(component1);
        lists.add(component2);
        String json = mapper.writeValueAsString(lists);
        System.out.println(json);
        List res = (ArrayList) mapper.readValue(json, ArrayList.class);

        List myRes = (ArrayList) ObjectJsonMapper.toObject(json, ArrayList.class);

        lists.forEach(System.out::println);
        System.out.println();
        res.forEach(System.out::println);
        myRes.forEach(System.out::println);
        assertThat(myRes).isEqualTo(res);
    }*/
}