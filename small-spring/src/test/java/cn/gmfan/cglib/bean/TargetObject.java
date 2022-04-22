package cn.gmfan.cglib.bean;

/**
 * @author gmfan
 */
public class TargetObject {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TargetObject{" +
                "name='" + name + '\'' +
                '}';
    }
}
