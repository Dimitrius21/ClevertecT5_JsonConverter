package bzh.json.parser.testObject;


import java.util.Objects;

public class Item {
    private String name;
    private Component component;

    public Item(String name, Component component) {
        this.name = name;
        this.component = component;
    }

    public Item() {
    }

    public String getName() {
        return name;
    }

    public Component getComponent() {
        return component;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (!Objects.equals(name, item.name)) return false;
        return Objects.equals(component, item.component);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (component != null ? component.hashCode() : 0);
        return result;
    }
}
