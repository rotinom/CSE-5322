package RocketUML.model;

import RocketUML.visitor.CodeGenerationController;
import org.junit.Test;

/**
 * Created by rotinom on 3/3/14.
 */
public class ProjectElementTest {

    @Test
    public void testProject(){
        ProjectElement pe = ProjectElement.create();
        DiagramElement d1 = pe.createDiagram();

        ClassElement base = d1.createClass().setName("base");

        ClassElement c1 = d1.createClass().setName("foo");
        AttributeElement ae1 = c1.createAttribute().setName("attrib1");
        ae1.setProtection(ProtectionEnum.PRIVATE);
        ae1.setType("String");

        // Use the builder pattern
        RelationshipElement re =
            d1.createRelationship()
                .setType(RelationshipType.Inheritance)
                .setSrce(c1)
                .setDest(base);


        MethodElement me1 = c1.createMethod().setName("method1");
        me1.setProtection(ProtectionEnum.PUBLIC);
        me1.setReturnType("void");

        MethodParameter mp1 = me1.createParameter().setName("param1");
        mp1.setType("float");

        MethodParameter mp2 = me1.createParameter().setName("param2");
        mp2.setType("std::string");



        ClassElement c2 = d1.createClass().setName("Class2");

        CodeGenerationController cgc = CodeGenerationController.create();
        cgc.generateCppCode(pe, ".");
        cgc.generateJavaCode(pe, ".");
    }

//    @org.junit.Test
//    public void testAccept() throws Exception {
//
//    }
}
