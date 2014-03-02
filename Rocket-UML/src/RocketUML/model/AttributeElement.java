package RocketUML.model;

import RocketUML.visitor.Visitor;

/**
 * Created by rotinom on 2/26/14.
 */
public class AttributeElement extends AbstractElement{

    private String name;
    private String type;


    private AttributeElement(){}

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public String getName() {
        return name;
    }

    public static AttributeElement create() {
        return new AttributeElement();
    }

    public void setName(String n) {
        name = n;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
