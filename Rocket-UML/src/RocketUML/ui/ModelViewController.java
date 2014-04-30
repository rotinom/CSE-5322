package RocketUML.ui;

import RocketUML.memento.Caretaker;
import RocketUML.memento.Memento;
import RocketUML.memento.Originator;
import RocketUML.model.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * Represents a View Controller
 */
public class ModelViewController {
    private int xOffset = 0;
    private int yOffset = 0;
    private AbstractElement selectedElement = null; //keep current element to facilitate modifications
    private String currentDiagram;

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
        AbstractElement element = AbstractFactory.getElement(type, x, y);
        if(ProjectElement.getInstance().getDiagram(currentDiagram) != null) {
            ProjectElement.getInstance().getDiagram(currentDiagram).addElement(element);
        }
        selectedElement = element;
        SaveState();
    }

    /**
     * Set location of currently selected element
     */
    public void setLocation(int x, int y) {
        if(selectedElement != null) {
            //special handling for Relationship
            if(selectedElement.getClass() == RelationshipElement.class) {

                if(!((RelationshipElement)selectedElement).getIsDragText()) {
                    ArrayList<ClassElement> classes = ProjectElement.getInstance().getDiagram(currentDiagram).getClasses();
                    for (ClassElement classElem : classes){
                        classElem.drawConnectPoints(classElem.closeTo(new Point(x, y)));
                        classElem.setRelationshipDragPoint(((RelationshipElement) selectedElement).getDragPoint());
                    }
                }
                selectedElement.setLocation(x, y);
            }
            else { //Class element
                selectedElement.setLocation(x - xOffset, y - yOffset);
            }
        }
        if(selectedElement == null) {
            SaveState();
        }
    }

    /**
     * Sets the currently selected element based on a x/y position
     */
    public void setSelectedElement(int mouseX, int mouseY){
        if(selectedElement != null)
            selectedElement.setSelected(false);

        selectedElement = null;
        if(ProjectElement.getInstance().getDiagram(currentDiagram) != null) {
            for (AbstractElement testElement : ProjectElement.getInstance().getDiagram(currentDiagram).getClasses()){
                if (testElement.contains(new Point(mouseX, mouseY))){
                    selectedElement = testElement;
                    break;
                }
            }

            if(selectedElement == null) {
                for (AbstractElement testElement : ProjectElement.getInstance().getDiagram(currentDiagram).getRelationships()){
                    if (testElement.contains(new Point(mouseX, mouseY))){
                        selectedElement = testElement;
                        break;
                    }
                }
            }
        }
          
        if(selectedElement != null){
            //save off offset between shape and mouse for dragging
            xOffset = mouseX - selectedElement.getX();
            yOffset = mouseY - selectedElement.getY();
            selectedElement.setSelected(true);
        }
    }

    public void changeRelationshipType(RelationshipType type) {
        if(selectedElement != null && selectedElement.getClass() == RelationshipElement.class) {
            ((RelationshipElement) selectedElement).setType(type);
        }
        SaveState();
    }

    public void addClassAttribute(String attribute) {
        if(selectedElement != null && selectedElement.getClass() == ClassElement.class) {
            ((ClassElement)selectedElement).addAttribute(attribute);
        }
        SaveState();
    }

    public void addClassMethod(String method) {
        if(selectedElement != null && selectedElement.getClass() == ClassElement.class) {
            ((ClassElement)selectedElement).addMethod(method);
        }
        SaveState();
    }

    public void editString(int mouseX, int mouseY) {
        if(selectedElement != null) {
            selectedElement.editString(mouseX, mouseY);
        }
        SaveState();
    }

    public void removeAttribute(int mouseX, int mouseY){
        if(selectedElement != null && selectedElement.getClass() == ClassElement.class) {
            ((ClassElement)selectedElement).removeAttribute(getStringAtLocation(mouseX, mouseY));
        }
        SaveState();
    }

    public void removeMethod(int mouseX, int mouseY){
        if(selectedElement != null && selectedElement.getClass() == ClassElement.class) {
            ((ClassElement)selectedElement).removeMethod(getStringAtLocation(mouseX, mouseY));
        }
        SaveState();
    }

    public void changeProtection(int mouseX, int mouseY, ProtectionEnum protection){
        if(selectedElement != null && selectedElement.getClass() == ClassElement.class) {
            ((ClassElement)selectedElement).changeProtection(mouseX, mouseY, protection);
        }
        SaveState();
    }

    public void removeElement() {
        if(ProjectElement.getInstance().getDiagram(currentDiagram) != null && selectedElement != null) {
            ProjectElement.getInstance().getDiagram(currentDiagram).removeElement(selectedElement);
            selectedElement = null;
        }
        SaveState();
    }

    public void drawElement(Graphics g) {
        if(ProjectElement.getInstance().getDiagram(currentDiagram) != null) {
            for(ClassElement elem : ProjectElement.getInstance().getDiagram(currentDiagram).getClasses()) {
                elem.draw(g);
            }
            for(RelationshipElement elem : ProjectElement.getInstance().getDiagram(currentDiagram).getRelationships()) {
                elem.draw(g);
            }
        }
    }

    public boolean isClassSelected() {
        boolean retVal = false;
        if(selectedElement != null) {
            retVal = (selectedElement.getClass() == ClassElement.class);
        }
        return retVal;
    }

    public boolean isRelationshipSelected() {
        boolean retVal = false;
        if(selectedElement != null) {
            retVal = (selectedElement.getClass() == RelationshipElement.class);
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
        if(selectedElement != null && selectedElement.getClass() == ClassElement.class) {
            retVal = ((ClassElement)selectedElement).isPointInAttributeArea(mouseX, mouseY);
        }
        return retVal;
    }

    public boolean isPointInMethodArea(int mouseX, int mouseY) {
        boolean retVal = false;
        if(selectedElement != null && selectedElement.getClass() == ClassElement.class) {
            retVal = ((ClassElement)selectedElement).isPointInMethodArea(mouseX, mouseY);
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

    public ArrayList<String> getDiagramNames() {
        ArrayList<String> names = new ArrayList<String>();
        for (String key : ProjectElement.getInstance().getDiagrams().keySet()) {
            names.add(key);
        }
        return names;
    }

    public void setRelationSrcMulti(String multi) {
        if(selectedElement != null) {
            ((RelationshipElement)selectedElement).setSrceMultiplicity(multi);
        }
    }

    public void setRelationDestMulti(String multi) {
        if(selectedElement != null) {
            ((RelationshipElement)selectedElement).setDestMultiplicity(multi);
        }
    }

    public String getCurrentDiagram(){
        return currentDiagram;
    }

    public void setCurrentDiagram(String currentDiagram){
        this.currentDiagram = currentDiagram;
        if(ProjectElement.getInstance().getDiagram(currentDiagram) == null) {
            ProjectElement.getInstance().createDiagram(currentDiagram);
        }
    }

    public void removeDiagram(String name) {
        ProjectElement.getInstance().removeDiagram(name);
    }

    public void changeDiagramName(String oldName, String newName) {
        ProjectElement.getInstance().changeName(oldName, newName);
    }

    public void resetController()
    {
        AbstractFactory.reset();
        xOffset = 0;
        yOffset = 0;
        selectedElement = null;
    }

    public void SaveState()
    {
        Originator.getInstance().setState(ProjectElement.getInstance().getDiagrams());
        Memento memento = Originator.getInstance().createMemento();
        Caretaker.getInstance().setState(memento);
    }

    public void UndoState()
    {
        Memento memento = Caretaker.getInstance().getUndoState();
        if(memento != null) {
            Originator.getInstance().setMemento(memento);
        }
        ProjectElement.getInstance().rebuildDiagrams(Originator.getInstance().getState());
    }

    public void RedoState()
    {
        Memento memento = Caretaker.getInstance().getRedoState();
        if(memento != null) {
            Originator.getInstance().setMemento(memento);
        }
        ProjectElement.getInstance().rebuildDiagrams(Originator.getInstance().getState());
    }
}
