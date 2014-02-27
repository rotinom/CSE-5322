package RocketUML.model;

/**
 * Created by rotinom on 2/26/14.
 */
public class AttributeElement {

    private String name = new String();


    private AttributeElement(){}

    public String getName() {
        return name;
    }

    public static AttributeElement create() {
        return new AttributeElement();
    }

    public void setName(String n) {
        name = n;
    }
}
