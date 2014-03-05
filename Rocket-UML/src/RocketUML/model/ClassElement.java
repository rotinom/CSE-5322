package RocketUML.model;

import RocketUML.ui.Drawable;
import RocketUML.visitor.Visitor;

import java.util.HashMap;
import java.util.List;

/**
 * Created by rotinom on 2/19/14.
 */
public class ClassElement extends AbstractElement implements Drawable {

    private String name_ = new String();
    private HashMap<String, AttributeElement> attributes =
            new HashMap<String, AttributeElement>();

    private HashMap<String, MethodElement> methods =
            new HashMap<String, MethodElement>();

    /**
     * CTOR
     */
    public ClassElement(String name) {
        name_ = name;
    }

    @Override
    public void accept(Visitor v) {

    }

    @Override
    public void draw() {
    }

    @Override
    public void destroy() {
    }

    /**
     * Add an attribute to the class
     * @param ae The attribute to add
     */
    public void addAttribute(AttributeElement ae){
        attributes.put(ae.getName(), ae);
    }

    /**
     * Get the attribute HashMap
     * @return The attribute HashMap
     */
    public HashMap<String, AttributeElement> getAttributes(){
        return attributes;
    }

    /**
     * Remove an attribute by its name
     * @param name The name of the attribute to remove
     * @return The attribute removed or null if it was not found
     */
    public AttributeElement removeAttributeByName(String name){
        AttributeElement ret = null;

        if(attributes.containsKey(name)){
            ret = attributes.get(name);
            attributes.remove(name);
        }
        return ret;
    }

    /**
     * Add a method to the class
     * @param m The method to add
     */
    public void addMethod(MethodElement m){
        methods.put(m.getName(), m);
    }

    /**
     * Get the methods in the class
     * @return The HashMap of the methods in the class
     */
    public HashMap<String, MethodElement> getMethods(){
        return methods;
    }

    /**
     * Set the name of the class
     * @param n The name to set
     */
    public void setName(String n){
        name_ = n;
    }
}
