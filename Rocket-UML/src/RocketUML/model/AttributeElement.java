package RocketUML.model;

import RocketUML.visitor.Visitor;

/**
 * Created by rotinom on 2/26/14.
 */
public class AttributeElement extends AbstractElement {

    private String name;
    private String type;
    private String protection;


    private AttributeElement() {
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public static AttributeElement create() {
        return new AttributeElement();
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

    public String getProtection() {
        return protection;
    }

    public void setProtection(String p) {
        protection = p;
    }
}
