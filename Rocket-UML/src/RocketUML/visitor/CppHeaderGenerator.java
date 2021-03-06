package RocketUML.visitor;

import RocketUML.model.*;
//import com.sun.tools.doclets.internal.toolkit.util.DocFinder;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rotinom on 3/1/14.
 */
public class CppHeaderGenerator extends Visitor implements CodeGenerator{
    private StringBuffer output = new StringBuffer();

    private ArrayList<AbstractElement> priv_elems = new ArrayList<AbstractElement>();
    private ArrayList<AbstractElement> prot_elems = new ArrayList<AbstractElement>();
    private ArrayList<AbstractElement> pub_elems = new ArrayList<AbstractElement>();

    private HashMap<ProtectionEnum, ArrayList<AbstractElement>> elem_map =
            new HashMap<ProtectionEnum, ArrayList<AbstractElement>>();

    private HashMap<String, String> inheritanceMap = new HashMap<String, String>();

    public CppHeaderGenerator() {
        elem_map.put(ProtectionEnum.PRIVATE,   priv_elems);
        elem_map.put(ProtectionEnum.PROTECTED, prot_elems);
        elem_map.put(ProtectionEnum.PUBLIC,    pub_elems);
    }

    /**
     * Internal visitor class used to print out the attributes
     * and methods exclusively
     */
    private class OutputVisitor extends Visitor{

        @Override
        public void visit(AttributeElement data) {
            output.append("    " + data.getType() + " " + data.getName() + ";\n");
        }

        @Override
        public void visit(MethodElement data) {
            output.append("    " + data.getReturnType() + " " + data.getName() + "(");

            for(int i = 0; i < data.getParameters().size(); ++i){
                data.getParameters().get(i).accept(this);
                if(i < data.getParameters().size()-1){
                    output.append(", ");
                }
            }
            output.append(");\n\n");
        }

        @Override
        public void visit(MethodParameter data) {
            output.append(data.getType() + " " + data.getName());
        }
    }


    @Override
    public void visit(ClassElement data) {
        output.append("class " + data.getName() + " ");

        if(inheritanceMap.containsKey(data.getName())){
            output.append(": public " + inheritanceMap.get(data.getName()) + " ");
        }
        output.append(" {\n");

        // Clear our lists
        priv_elems.clear();
        prot_elems.clear();
        pub_elems.clear();

        // catalog our elements so we can group by protection
        for(AttributeElement ae : data.getAttributes()){
            ae.accept(this);
        }

        for(MethodElement me : data.getMethods()){
            me.accept(this);
        }

        OutputVisitor ov = new OutputVisitor();

        output.append("\npublic:\n");

        // CTOR/DTOR
        output.append("    " + data.getName() + "();\n");
        output.append("    virtual ~" + data.getName() + "();\n");

        for(AbstractElement ae : pub_elems) {
            ae.accept(ov);
        }

        output.append("\nprotected:\n");
        for(AbstractElement ae : prot_elems) {
            ae.accept(ov);
        }

        output.append("\nprivate:\n");
        for(AbstractElement ae : priv_elems) {
            ae.accept(ov);
        }

        output.append("};\n\n");
    }

    @Override
    public void visit(AttributeElement data) {
        elem_map.get(data.getProtection()).add(data);
    }

    @Override
    public void visit(MethodElement data) {
        elem_map.get(data.getProtection()).add(data);
    }

    @Override
    public void visit(ProjectElement data) {
        output.append("namespace " + data.getName() + "{\n");
        for(DiagramElement de : data.getDiagrams().values()){
            de.accept(this);
        }
        output.append("} // namespace " + data.getName() + "\n");
    }

    @Override
    public void visit(DiagramElement data) {
        output.append("namespace " + data.getName() + "{\n");

        // Catalog the relationships so we can get the inheritances
        for(RelationshipElement re : data.getRelationships()){
            re.accept(this);
        }

        // Output our classes
        for(ClassElement ce : data.getClasses()){
            ce.accept(this);
        }

        output.append("} // namespace " + data.getName() + "\n");
    }

    @Override
    public void visit(RelationshipElement data) {
        // We only care if the relationship is a "inheritance" type
        if(data.getType() != RelationshipType.Inheritance){
            return;
        }

        inheritanceMap.put(data.getSrce().getName(), data.getDest().getName());
    }

    public String toString(){
        return output.toString();
    }

}
