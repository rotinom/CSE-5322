package RocketUML.visitor;

import RocketUML.model.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rotinom on 3/1/14.
 */
public class JavaGenerator extends Visitor implements CodeGenerator{
    private StringBuffer output = new StringBuffer();
    private String output_dir;
    private ProjectElement project;
    private DiagramElement diagram;
    private HashMap<String, String> inheritanceMap = new HashMap<String, String>();

    public JavaGenerator(String outputDir) {
        output_dir = outputDir;
    }

    @Override
    public void visit(ProjectElement data) {
        project = data;
        for(DiagramElement de : data.getDiagrams().values()){
            de.accept(this);
        }
    }

    @Override
    public void visit(DiagramElement data) {
        diagram = data;

        for(RelationshipElement re: data.getRelationships()){
            re.accept(this);
        }

        for(ClassElement ce : data.getClasses()){
            ce.accept(this);
        }
    }

    @Override
    public void visit(ClassElement data) {

        // Clear the output
        output.setLength(0);

        output.append("package " + project.getName() + "." + diagram.getName() + ";\n");
        output.append("class " + data.getName() + " ");
        if(inheritanceMap.containsKey(data.getName())){
            output.append("extends " + inheritanceMap.get(data.getName()) + " ");
        }
        output.append(" {\n");

        for(AttributeElement ae : data.getAttributes()){
            ae.accept(this);
        }

        for(MethodElement ae : data.getMethods()){
            ae.accept(this);
        }

        output.append("};\n\n");

        try {
            // Create the destination path
            String out_path = output_dir + "/" + project.getName() + "/" + diagram.getName();
            File dir = new File(out_path);
            dir.mkdirs();

            // Create the destination file
            OutputStream file = new FileOutputStream(out_path + "/" + data.getName() + ".java");
            file.write(output.toString().getBytes());
            file.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getProtection(ProtectionEnum prot){
        switch(prot){
            case PUBLIC:
                return "public";
            case PROTECTED:
                return "protected";
            case PRIVATE:
                return "private";
            default:
                return "";
        }
    }

    private String getProtection(AttributeElement data){
        return getProtection(data.getProtection());
    }

    private String getProtection(MethodElement data){
        return getProtection(data.getProtection());
    }


    @Override
    public void visit(AttributeElement data) {
        output.append("    " + getProtection(data) + " " + data.getType() + " " + data.getName() + ";\n");
    }

    @Override
    public void visit(MethodElement data) {
        output.append("    " + getProtection(data) + " " + data.getReturnType() + " " + data.getName() + "(");
        for(int i = 0; i < data.getParameters().size(); ++i){
            MethodParameter mp = data.getParameters().get(i);

            mp.accept(this);

            if(i < data.getParameters().size() -1){
                output.append(", ");
            }
        }
        output.append("){\n    }\n");
    }

    @Override
    public void visit(RelationshipElement data) {
        // We only care if the relationship is a "inheritance" type
        if(data.getType() != RelationshipType.Inheritance){
            return;
        }

        inheritanceMap.put(data.getSrce().getName(), data.getDest().getName());
    }

    @Override
    public void visit(MethodParameter data) {
        output.append(data.getType() + " " + data.getName());
    }

    public String toString(){
        return output.toString();
    }


}
