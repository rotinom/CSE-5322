package RocketUML.model;

import RocketUML.visitor.Visitor;

import javax.management.Attribute;

/**
 * Created by rotinom on 2/26/14.
 */
public class AttributeElement extends AbstractElement {

    private String type;
    private ProtectionEnum protection;


    private AttributeElement() {
        protection = ProtectionEnum.PUBLIC;
        type = "int";
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public static AttributeElement create() {
        return new AttributeElement();
    }

    public AttributeElement setName(String n) {
        super.setName(n);
        return this;
    }

    public String getType() {
        return type;
    }

    public AttributeElement setType(String t) {
        type = t;
        return this;
    }

    public ProtectionEnum getProtection() {
        return protection;
    }

    public AttributeElement setProtection(ProtectionEnum p) {
        protection = p;
        return this;
    }

    public String getString() {
        return type + " " + getName();
    }

    public void setString(String str) {
        String cleanStr = str.replaceAll(";", "");
        String delims = " +";
        String[] tokens = cleanStr.split(delims);

        if(tokens.length > 1) {
            type = tokens[0];
            setName(tokens[1]);
        }
    }
}
