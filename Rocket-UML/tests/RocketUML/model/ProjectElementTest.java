package RocketUML.model;

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
        ClassElement c2 = d1.createClass("bar");
    }

//    @org.junit.Test
//    public void testAccept() throws Exception {
//
//    }
}
