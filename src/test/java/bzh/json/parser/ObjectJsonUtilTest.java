package bzh.json.parser;


import bzh.json.parser.Helpers.ObjectJsontUtil;
import bzh.json.parser.testObject.Component;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

class ObjectJsonUtilTest {

    @Test
    void isClassMap() {
        boolean res = ObjectJsontUtil.isClassImplementInterface(SortedMap.class, Map.class);
        assertThat(res).isTrue();
    }

    @Test
    void isClassCollection() {
        boolean res = ObjectJsontUtil.isClassImplementInterface(ArrayList.class, Collection.class);
        assertThat(res).isTrue();
    }

    @Test
    void getListOfString() {
        String test = "\"a,f\", \"gtrg\"";
        List<String> res = ObjectJsontUtil.getListOfString(test);
        assertThat(res).hasSize(2).contains("\"a,f\"", "\"gtrg\"");
    }


    @Test
    void getFirstString() {
        String test = "\"a,f\", \"gtrg\"";
        String res = ObjectJsontUtil.getFirstString(test, 0);
        assertThat(res).isEqualTo("\"a,f\"");
    }

    @Test
    void getListObject() {
        String test = "[][\"a,f\", \"el2\"], [\"gtrg\", \"el4\"]";
        List<String> res = ObjectJsontUtil.getListObject(test);
        assertThat(res).hasSize(2).contains("[\"a,f\", \"el2\"]", "[\"gtrg\", \"el4\"]");
    }

    @Test
    void getParseMap() {
        Map<String, String> testMap = new HashMap<>();
        testMap.put("11", "k");
        testMap.put("22", "k2");
        String test = "\"11\" : \"k\", \"22\":\"k2\"";
        Map res = ObjectJsontUtil.getParseMap(test, new HashMap());
        assertThat(res).isEqualTo(testMap);
    }

    @Test
    void createObject(){
        Class clazz = Component.class;
        Component component = (Component) ObjectJsontUtil.createObject(clazz);
        assertThat(component).isNotNull().isInstanceOf(Component.class);
    }
}