package RocketUML.model;

import RocketUML.ui.Drawable;
import RocketUML.visitor.Visitor;

/**
 * Created by rotinom on 3/1/14.
 */
public class MethodParameter extends AbstractElement {
    private String type;
    private String name;

    private MethodParameter(String n){
        name = n;
    }

    public static MethodParameter create(String n){
        return new MethodParameter(n);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
