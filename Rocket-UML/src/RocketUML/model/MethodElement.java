package RocketUML.model;

import RocketUML.visitor.Visitor;

import java.util.ArrayList;

/**
 * Created by rotinom on 2/26/14.
 */
public class MethodElement extends AbstractElement{
    private ProtectionEnum protection; /// public/protected/private
    private String name;       /// name
    private String returnType; /// return value

    private ArrayList<MethodParameter> parameters = new ArrayList<MethodParameter>();

    private MethodElement(String n){
        name = n;
    }

    public static MethodElement create(String name) {
        return new MethodElement(name);
    }

    public MethodParameter createParameter(String name){
        MethodParameter ret = MethodParameter.create(name);
        parameters.add(ret);
        return ret;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ProtectionEnum getProtection() {
        return protection;
    }

    public void setProtection(ProtectionEnum protection) {
        this.protection = protection;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public ArrayList<MethodParameter> getParameters() {
        return parameters;
    }
}
