package RocketUML.visitor;

import RocketUML.model.*;

import java.util.HashMap;

/**
 * Created by rotinom on 3/1/14.
 */
public class CppCodeGenerator extends Visitor{
    private StringBuffer priv = new StringBuffer();
    private StringBuffer prot = new StringBuffer();
    private StringBuffer pub  = new StringBuffer();

    private HashMap<String, StringBuffer> buffers = new HashMap<String, StringBuffer>();

    public CppCodeGenerator() {
        buffers.put("private", priv);
        buffers.put("public", pub);
        buffers.put("protected", prot);
    }


    @Override
    public void visit(ClassElement data) {

    }

    @Override
    public void visit(AttributeElement data) {
        buffers.get(data.getProtection()).append(
                data.getType() + " " + data.getName() + ";"
        );
    }

    @Override
    public void visit(MethodElement data) {

    }

    @Override
    public void visit(ProjectElement data) {

    }

    @Override
    public void visit(RelationshipElement data) {

    }

    /**
     * Clear the visitor
     */
    public void clear(){
        priv.setLength(0);
        prot.setLength(0);
        pub.setLength(0);
    }

    public String toString(){
        StringBuffer output = new StringBuffer();
        getHppHeader(output);

        return output.toString();
    }

    private void getHppHeader(StringBuffer output){
        String h =
              "#pragma once\n"
            + "\n"
            + "\n"
            + "class ";

}
