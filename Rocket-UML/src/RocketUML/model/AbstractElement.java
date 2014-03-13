package RocketUML.model;

import RocketUML.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by rotinom on 2/19/14.
 */





/**
 * Represents an abstract element
 */
public abstract class AbstractElement {

    private String name;

    protected AbstractElement(){
        name = this.getClass().getSimpleName() + "-" + UUID.randomUUID().toString().replace("-", "");
    }

    public abstract void accept(Visitor v);

    public String getName() {
        return name;
    }

    public AbstractElement setName(String n) {
        name = n;
        return this;
    }
}
