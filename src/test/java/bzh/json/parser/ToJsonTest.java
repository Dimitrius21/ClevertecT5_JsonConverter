package bzh.json.parser;

import bzh.json.parser.ObjectJsonMapper;
import bzh.json.parser.testObject.Component;
import bzh.json.parser.testObject.Item;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToJsonTest {
    private static ObjectMapper mapper;

    @BeforeAll
    public static void initJackson() {
        mapper = new ObjectMapper();
    }

    @Test
    public void simpleStringToJson() throws JsonProcessingException, IllegalAccessException {
        String obj = "SimpleString";
        String exp = mapper.writeValueAsString(obj);
        String res = ObjectJsonMapper.toJson(obj);
        assertThat(res).isEqualTo(exp);
    }

    @Test
    public void simpleNumberToJson() throws JsonProcessingException, IllegalAccessException {
        Double obj = 89.56;
        String exp = mapper.writeValueAsString(obj);
        String res = ObjectJsonMapper.toJson(obj);
        assertThat(res).isEqualTo(exp);
    }

    @Test
    public void simpleNumberArrayToJson() throws JsonProcessingException, IllegalAccessException {
        Double[] obj = {89.56, 789.36};
        String exp = mapper.writeValueAsString(obj);
        String res = ObjectJsonMapper.toJson(obj);
        assertThat(res).isEqualTo(exp);
    }

    @Test
    public void ObjectArrayToJson() throws JsonProcessingException, IllegalAccessException {
        Component component1 = new Component("Comp1", 5);
        Component component2 = new Component("Comp2", 15);
        Component[] obj = {component1, component2};
        String exp = mapper.writeValueAsString(obj);
        String res = ObjectJsonMapper.toJson(obj);
        assertThat(res).isEqualTo(exp);
    }

    @Test
    public void ObjectToJson() throws JsonProcessingException, IllegalAccessException {
        Item obj = new Item("Pos1", new Component("Component1", 23));
        //Double[] obj = {89.56, 789.36};
        String exp = mapper.writeValueAsString(obj);
        String res = ObjectJsonMapper.toJson(obj);
        assertThat(res).isEqualTo(exp);
    }

    @Test
    public void ListToJson() throws JsonProcessingException, IllegalAccessException {
        Component component1 = new Component("Comp1", 5);
        Component component2 = new Component("Comp2", 15);
        List<Component> obj = new ArrayList<>();
        obj.add(component1);
        obj.add(component2);
        String exp = mapper.writeValueAsString(obj);
        String res = ObjectJsonMapper.toJson(obj);
        assertThat(res).isEqualTo(exp);
    }
    @Test
    public void MapToJson() throws JsonProcessingException, IllegalAccessException {
        Component component1 = new Component("Comp1", 5);
        Map<String, Component> obj = new HashMap<>();
        obj.put("key", component1);
        String exp = mapper.writeValueAsString(obj);
        String res = ObjectJsonMapper.toJson(obj);
        assertThat(res).isEqualTo(exp);
    }
}
