package RocketUML.model;

import RocketUML.visitor.Visitor;

/**
 * Created by rotinom on 2/26/14.
 */
public class AttributeElement extends AbstractElement {

    private String name;
    private String type;
    private ProtectionEnum protection;


    private AttributeElement(String n) {
        name = n;
        protection = ProtectionEnum.PUBLIC;
        type = "int";
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public static AttributeElement create(String name) {
        return new AttributeElement(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }

    public String getType() {
        return type;
    }

    public void setType(String t) {
        type = t;
    }

    public ProtectionEnum getProtection() {
        return protection;
    }

    public void setProtection(ProtectionEnum p) {
        protection = p;
    }
}
