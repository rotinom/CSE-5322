package RocketUML.model;

import RocketUML.visitor.Visitor;

/**
 * Represents a class Attribute element
 */
public class AttributeElement extends AbstractElement {

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

    /**
     * Get formatted string with appropriate symbology for protection
     */
    public String getString() {
        String string = "";
        switch (protection) {
            case PUBLIC: string += "+ "; break;
            case PRIVATE: string += "- "; break;
            case PROTECTED: string += "# "; break;
            default: string += " "; break;
        }
        return string + type + " " + name;
    }

    /**
     * Parse out and tokenize the string to get name/type
     */
    public void setString(String str) {
        protection = ProtectionEnum.PUBLIC;
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
