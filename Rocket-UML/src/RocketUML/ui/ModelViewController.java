package RocketUML.ui;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;


public class ModelViewController {
    int classCounter = 0;
    int relationshipCounter = 0;
    private int xOffset = 0;
    private int yOffset = 0;

    private int undoCounter = 0;

    private Element selectedElement = null; //keep current element to facilitate modifications
    private String currentDiagram;

    protected HashMap<String, ArrayList<Element>> elements = new HashMap<String, ArrayList<Element>>();
    private Stack<HashMap<String, ArrayList<Element>>> savedStates
            = new Stack<HashMap<String, ArrayList<Element>>>();


    private static ModelViewController instance_ = null;

    private ModelViewController() {	}

    public static synchronized ModelViewController getInstance() {
        if (instance_ == null) {
            instance_ = new ModelViewController ();
        }
        return instance_;
    }

    public void createElement(String name, String type, int x, int y)
    {
        Element element = AbstractFactory.getElement(type);
        if (name.equals(""))
        {
            if (type.equals("Class"))
            {
                element.init(x, y, "New Class " + classCounter++, type);
            }
            else
            {
                element.init(x, y, "New Relationship " + relationshipCounter++, type);
            }
        }
        else
        {
            element.init(x, y, name, type);
        }

        if(elements.containsKey(currentDiagram)) {
            elements.get(currentDiagram).add(element);
            saveToMemento();
        }

        selectedElement = element;
    }

    public void setLocation(int x, int y) {
        if(selectedElement != null) {
            //special handling for Relationship
            if(selectedElement.getClass() == Relationship.class) {

                if(!((Relationship)selectedElement).getIsDragText()) {
                    ArrayList<Element> elem = elements.get(currentDiagram);
                    for (Element element : elem){
                        if(element.getClass() == Class.class){
                            ((Class)element).drawConnectPoints(((Class)element).closeTo(new Point(x,y)));
                            ((Class)element).setRelationshipDragPoint(((Relationship)selectedElement).getDragPoint());
                        }
                    }
                }
                selectedElement.setLocation(x, y);
            }
            else { //Class element
                selectedElement.setLocation(x - xOffset, y - yOffset);
            }
        }
    }

    public void setSelectedElement(int mouseX, int mouseY){
        if(selectedElement != null)
            selectedElement.setSelected(false);

        selectedElement = null;
        if(elements.containsKey(currentDiagram)) {
            ArrayList<Element> elem = elements.get(currentDiagram);
            for (Element testElement : elem){
                if (testElement.contains(new Point(mouseX, mouseY))){
                    selectedElement = testElement;
                    //save off offset between shape and mouse
                    xOffset = mouseX - selectedElement.getX();
                    yOffset = mouseY - selectedElement.getY();
                    selectedElement.setSelected(true);
                    break;
                }
            }
        }
    }

    public void changeRelationshipType(Relationship.Type type) {
        if(selectedElement != null && selectedElement.getClass() == Relationship.class) {
            ((Relationship) selectedElement).setType(type);
        }
    }

    public void addClassAttribute(String attribute) {
        if(selectedElement != null && selectedElement.getClass() == Class.class) {
            ((Class)selectedElement).addAttribute(attribute);
        }
    }

    public void addClassMethod(String method) {
        if(selectedElement != null && selectedElement.getClass() == Class.class) {
            ((Class)selectedElement).addMethod(method);
        }
    }

    public void editString(int mouseX, int mouseY) {
        if(selectedElement != null) {
            selectedElement.editString(mouseX, mouseY);
        }
    }

    public void removeAttribute(int mouseX, int mouseY){
        if(selectedElement != null && selectedElement.getClass() == Class.class) {
            ((Class)selectedElement).removeAttribute(getStringAtLocation(mouseX, mouseY));
        }
    }

    public void removeMethod(int mouseX, int mouseY){
        if(selectedElement != null && selectedElement.getClass() == Class.class) {
            ((Class)selectedElement).removeMethod(getStringAtLocation(mouseX, mouseY));
        }
    }

    public void removeElement() {
        if(elements.containsKey(currentDiagram) && selectedElement != null) {
            ArrayList<Element> elem = elements.get(currentDiagram);
            elem.remove(selectedElement);
            selectedElement = null;
        }
    }

    public void drawElement(Graphics g) {
        if(elements.containsKey(currentDiagram)) {
            ArrayList<Element> elem = elements.get(currentDiagram);
            for (Element s : elem){
                s.draw(g);
            }
        }
    }

    public boolean isClassSelected() {
        boolean retVal = false;
        if(selectedElement != null) {
            retVal = (selectedElement.getClass() == Class.class);
        }
        return retVal;
    }

    public boolean isRelationshipSelected() {
        boolean retVal = false;
        if(selectedElement != null) {
            retVal = (selectedElement.getClass() == Relationship.class);
        }
        return retVal;
    }

    public int getElementX() {
        int retVal = 0;
        if(selectedElement != null) {
            retVal = selectedElement.getX();
        }
        return retVal;
    }

    public int getElementY() {
        int retVal = 0;
        if(selectedElement != null) {
            retVal = selectedElement.getY();
        }
        return retVal;
    }

    public boolean isPointInAttributeArea(int mouseX, int mouseY) {
        boolean retVal = false;
        if(selectedElement != null && selectedElement.getClass() == Class.class) {
            retVal = ((Class)selectedElement).isPointInAttributeArea(mouseX, mouseY);
        }
        return retVal;
    }

    public boolean isPointInMethodArea(int mouseX, int mouseY) {
        boolean retVal = false;
        if(selectedElement != null && selectedElement.getClass() == Class.class) {
            retVal = ((Class)selectedElement).isPointInMethodArea(mouseX, mouseY);
        }
        return retVal;
    }

    public String getStringAtLocation(int mouseX, int mouseY){
        String retVal = "";
        if(selectedElement != null) {
            retVal = selectedElement.getStringAtLocation(mouseX, mouseY);
        }
        return retVal;
    }

    public boolean isElementSelected() {
        return (selectedElement != null);
    }

    public int getNumDiagrams() {
        return elements.size();
    }

    public ArrayList<String> getDiagramNames() {
        ArrayList<String> names = new ArrayList<String>();
        for (String key : elements.keySet()) {
            names.add(key);
        }
        return names;
    }

    public void serializeElements(String fileName)
    {
        Serialization ser = new Serialization();
        ser.Serialize(fileName, elements);
    }

    public void deserializeElements(String fileName)
    {
        Serialization deser = new Serialization();
        deser.Deserialize(fileName);
    }

    public void resetDiagramForOpen()
    {
        elements.clear();
        classCounter = 0;
        relationshipCounter = 0;
        xOffset = 0;
        yOffset = 0;
        selectedElement = null;
    }

    public void rebuildElementsArray(HashMap<String, ArrayList<Element>> loadElements)
    {
        elements.clear();
        for (String key : loadElements.keySet()) {
            elements.put(key, new ArrayList<Element>());
            ArrayList<Element> elementList = loadElements.get(key);
            for (Element element : elementList) {
                elements.get(key).add(element);
            }
        }
    }

    public String getCurrentDiagram(){
        return currentDiagram;
    }

    public void setCurrentDiagram(String currentDiagram){
        this.currentDiagram = currentDiagram;
        if(!elements.containsKey(currentDiagram)) {
            elements.put(currentDiagram, new ArrayList<Element>());
        }
    }

    public void removeDiagram(String name) {
        elements.remove(name);
    }

    public void changeDiagramName(String oldName, String newName) {
        if(elements.containsKey(oldName)) {
            ArrayList<Element> obj = elements.remove(oldName);
            elements.put(newName, obj);
        }
    }

    public void saveToMemento()
    {
         savedStates.push(this.elements);
    }

    public HashMap<String, ArrayList<Element>> undoMemento()
    {
        return savedStates.firstElement();
    }
}
