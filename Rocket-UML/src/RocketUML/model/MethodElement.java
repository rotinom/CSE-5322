package RocketUML.model;

import RocketUML.visitor.Visitor;

import java.util.ArrayList;

/**
 * Created by rotinom on 2/26/14.
 */
public class MethodElement extends AbstractElement{
    private ProtectionEnum protection; /// public/protected/private
    private String returnType; /// return value

    private ArrayList<MethodParameter> parameters = new ArrayList<MethodParameter>();

    private MethodElement(){
    }

    public static MethodElement create() {
        return new MethodElement();
    }

    public MethodParameter createParameter(){
        MethodParameter ret = MethodParameter.create();
        parameters.add(ret);
        return ret;
    }

    public MethodElement setName(String n) {
        super.setName(n);
        return this;
    }


    public ProtectionEnum getProtection() {
        return protection;
    }

    public MethodElement setProtection(ProtectionEnum protection) {
        this.protection = protection;
        return this;
    }

    public String getReturnType() {
        return returnType;
    }

    public MethodElement setReturnType(String returnType) {
        this.returnType = returnType;
        return this;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public ArrayList<MethodParameter> getParameters() {
        return parameters;
    }
}
