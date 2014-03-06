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
        ClassElement c1 = d1.createClass("foo");
        AttributeElement ae1 = c1.createAttribute("attrib1");
        ae1.setProtection(ProtectionEnum.PRIVATE);
        ae1.setType("std::string");


        MethodElement me1 = c1.createMethod("method1");
        me1.setProtection(ProtectionEnum.PUBLIC);
        me1.setReturnType("void");

        MethodParameter mp1 = me1.createParameter("param1");
        mp1.setType("float");

        MethodParameter mp2 = me1.createParameter("param2");
        mp2.setType("std::string");



        ClassElement c2 = d1.createClass("bar");

        CodeGenerationController cgc = CodeGenerationController.create();
        cgc.generateCppCode(pe, ".");
    }

//    @org.junit.Test
//    public void testAccept() throws Exception {
//
//    }
}
