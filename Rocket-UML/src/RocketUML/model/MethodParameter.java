package RocketUML.model;

import RocketUML.visitor.Visitor;

/**
 * Represents Method Parameter element
 */
public class MethodParameter extends AbstractElement {
    private String type;

    private MethodParameter(){
    }

    public static MethodParameter create(){
        return new MethodParameter();
    }

    public String getType() {
        return type;
    }

    public MethodParameter setType(String type) {
        this.type = type;
        return this;
    }

    public MethodParameter setName(String n) {
        super.setName(n);
        return this;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
