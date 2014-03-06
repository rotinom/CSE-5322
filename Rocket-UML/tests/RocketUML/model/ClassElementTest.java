package RocketUML.model;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by rotinom on 2/26/14.
 */
public class ClassElementTest {
    @Test
    public void testCreate() throws Exception {
        ClassElement ce = new ClassElement("foo");
        assertNotNull(ce);
    }

    @Test
    public void testAddAttribute() throws Exception {
//        ClassElement ce = new ClassElement("bar");
//        AttributeElement ae = AttributeElement.create();
//        ae.setName("foo");
//
//        // Make sure it's empty
//        assertEquals(ce.getAttributes().size(), 0);

//        // Make sure we add one
//        ce.addAttribute(ae);
//        assertEquals(ce.getAttributes().size(), 1);
//
//        // Make sure the element we added is in there
//        HashMap<String, AttributeElement> attribs = ce.getAttributes();
//        assertTrue(attribs.containsKey(ae.getName()));

    }

    @Test
    public void testRemoveAttributeByName() throws Exception {
//        ClassElement ce = new ClassElement("baz");
//        AttributeElement ae = AttributeElement.create();
//        ae.setName("foo");
//
//        // Make sure it's empty
//        assertEquals(ce.getAttributes().size(), 0);

//        // Make sure we add one
//        ce.addAttribute(ae);
//        assertEquals(ce.getAttributes().size(), 1);
//
//        ce.removeAttributeByName("foo");
//        assertEquals(ce.getAttributes().size(), 0);
    }

    @Test
    public void testAddMethod() throws Exception {

    }

    @Test
    public void testGetMethods() throws Exception {

    }

    @Test
    public void testSetName() throws Exception {

    }
}
