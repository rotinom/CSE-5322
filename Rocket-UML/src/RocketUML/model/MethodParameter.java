package RocketUML.model;

import RocketUML.ui.Drawable;
import RocketUML.visitor.Visitor;

/**
 * Created by rotinom on 3/1/14.
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
        type = type;
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
