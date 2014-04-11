package RocketUML.model;

import RocketUML.visitor.Visitor;

import javax.management.Attribute;
import java.io.Serializable;

/**
 * Created by rotinom on 2/26/14.
 */
public class AttributeElement extends AbstractElement implements Serializable {

    private String type;
    private ProtectionEnum protection;
    private String name;


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
        //return type + " " + getName();
        return type + " " + name;
    }

    public void setString(String str) {
        String cleanStr = str.replaceAll(";", "");
        String delims = " +";
        String[] tokens = cleanStr.split(delims);

        if(tokens.length > 1) {
            type = tokens[0];
            name = tokens[1];
            setName(tokens[1]);
        }
    }
}
