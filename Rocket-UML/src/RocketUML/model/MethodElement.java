package RocketUML.model;

import RocketUML.visitor.Visitor;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Represents Method element
 */
public class MethodElement extends AbstractElement {

    private ProtectionEnum protection; /// public/protected/private
    private String returnType; /// return value
    private String name;

    private ArrayList<MethodParameter> parameters = new ArrayList<MethodParameter>();

    private MethodElement(){}

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

    /**
     * Get formatted string with appropriate symbology for protection
     */
    public String getString() {
        String methodString = "";
        switch (protection) {
            case PUBLIC: methodString += "+ "; break;
            case PRIVATE: methodString += "- "; break;
            case PROTECTED: methodString += "# "; break;
            default: methodString += " "; break;
        }
        methodString += returnType + " " + name + "(";
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

    /**
     * Parse out and tokenize the string to get name/type
     */
    public void setString(String str) {

        boolean validString = true;
        String reason = "";
        protection = ProtectionEnum.PUBLIC;

        //check for ()
        if(!str.contains("(") || !str.contains(")")) {
            validString = false;
            reason = "Missing ()";
        }

        //check for return type and function name before () no more no less
        String[] parts = str.split("\\(");
        String[] parts2 = parts[0].split(" +");
        if(parts2.length != 2) {
            validString = false;
            reason = "Missing return type or method name";
            System.out.println("missing return type or method name");
        }

        parameters.clear();
        String cleanStr = str.replaceAll("\\(|\\)|;|,", " ");
        String delims = " +";
        String[] tokens = cleanStr.split(delims);

        //should have an even # of things (return type and name, then each parameter as a type name pair)
        if(tokens.length%2 != 0 && validString){
            validString = false;
            reason = "Invalid parameter pairs";
        }

        //if invalid notify user why
        if(!validString) {
            JOptionPane.showMessageDialog(null, "Invalid Method:\n"+reason+
                    "\n\nMust be in the format:\nreturnType methodName(param1Type param1Name, etc...)",
                    "Invalid Method", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        //get name and return type
        if(tokens.length > 1) {
            returnType = tokens[0];
            name = tokens[1];
            setName(tokens[1]);
        }

        //get parameters (in pairs)
        int i = 2;
        while (i+1 < tokens.length) {
            MethodParameter param = createParameter();
            param.setType(tokens[i]).setName(tokens[i + 1]);
            i += 2;
        }
    }
}

