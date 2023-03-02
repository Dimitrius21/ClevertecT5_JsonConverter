package bzh.json.parser.testObject;

import java.util.*;

public class Product {
    private String name = "Milk";
    private byte byteB = 5;
    private char charT = 'd';
    private short shortT = 32000;
    private int price = 200;
    private long longT = 2000000L;
    private boolean booleanT = true;
    private double weight = 1.2;
    private float floatT = 0.5f;
    private int[] arrayInt = {5, 10};
    private Integer[] IntegerArray = {20, 20};
    private List<Component> list = new ArrayList<>(Arrays
            .asList(new Component("plastic", 15), new Component("steel", 5)));
    private Map<String, String> map = new HashMap<>();


    public Product() {
        map.put("Key1", "value1");
        map.put("Key2", "value2");
    }

    public Map<String, String> getMap() {
        return map;
    }

    public String getName() {
        return name;
    }

    public byte getByteB() {
        return byteB;
    }

    public char getCharT() {
        return charT;
    }

    public short getShortT() {
        return shortT;
    }

    public int getPrice() {
        return price;
    }

    public long getLongT() {
        return longT;
    }

    public boolean isBooleanT() {
        return booleanT;
    }

    public double getWeight() {
        return weight;
    }

    public float getFloatT() {
        return floatT;
    }

    public int[] getArrayInt() {
        return arrayInt;
    }

    public Integer[] getIntegerArray() {
        return IntegerArray;
    }

    public List<Component> getList() {
        return list;
    }
}


