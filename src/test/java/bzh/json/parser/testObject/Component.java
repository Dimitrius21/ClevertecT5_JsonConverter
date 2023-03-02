package bzh.json.parser.testObject;

import java.util.Objects;

public class Component {
    private String componentName = "__";
    private int value = 0;

    public Component() {
    }

    public Component(String componentName, int value) {
        this.componentName = componentName;
        this.value = value;
    }

    public String getComponentName() {
        return componentName;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Component{" +
                "componentName='" + componentName + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Component component = (Component) o;

        if (value != component.value) return false;
        return Objects.equals(componentName, component.componentName);
    }

    @Override
    public int hashCode() {
        int result = componentName != null ? componentName.hashCode() : 0;
        result = 31 * result + value;
        return result;
    }
}
