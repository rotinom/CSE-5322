package RocketUML.model;

import RocketUML.ui.Drawable;
import RocketUML.visitor.Visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by rotinom on 2/19/14.
 */
public class ClassElement extends AbstractElement implements Drawable {

    private ArrayList<AttributeElement> attributes =
            new ArrayList<AttributeElement>();
    private ArrayList<MethodElement> methods =
            new ArrayList<MethodElement>();

    /**
     * CTOR
     */
    public ClassElement() {
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public void draw() {
    }

    @Override
    public void destroy() {
    }

    /**
     * Add an attribute to the class
     */
    public AttributeElement createAttribute(){
        AttributeElement ae = AttributeElement.create();
        attributes.add(ae);
        return ae;
    }

    /**
     * Add a method to the class
     * @return
     */
    public MethodElement createMethod(){
        MethodElement ae = MethodElement.create();
        methods.add(ae);
        return ae;
    }

    /**
     * Get the attribute HashMap
     * @return The attribute HashMap
     */
    public ArrayList<AttributeElement> getAttributes(){
        return attributes;
    }

    /**
     * Set the name of the class
     * @param n The name to set
     */
    public ClassElement setName(String n){
        super.setName(n);
        return this;
    }

    public ArrayList<MethodElement> getMethods() {
        return methods;
    }
}
