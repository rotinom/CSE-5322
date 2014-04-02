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

    public String getString() {
        String methodString = returnType + " " + getName() + "(";
        boolean first = true;
        for (MethodParameter parameter : parameters){
            if(!first) {
                methodString = methodString + ", ";
            }
            else {
                first = false;
            }
            methodString = methodString + parameter.getType() + " " + parameter.getName();
        }
        methodString = methodString + ")";
        return methodString;
    }

    public void setString(String str) {
        parameters.clear();
        String cleanStr = str.replaceAll("\\(|\\)|;|,", " ");
        String delims = " +";
        String[] tokens = cleanStr.split(delims);

        //System.out.println("method string = " + str);
        //System.out.println("method string = " + cleanStr);
        //System.out.println("delims = " + delims + " tokens.length="+tokens.length);

        if(tokens.length > 1) {
            returnType = tokens[0];
            setName(tokens[1]);
            //System.out.println("returnType="+returnType+" name="+getName());
        }
        int i = 2;
        while (i+1 < tokens.length) {
            MethodParameter param = createParameter();
            param.setType(tokens[i]).setName(tokens[i + 1]);
            //System.out.println("type=" + param.getType() + " name=" + param.getName());
            i += 2;
        }
    }
}

