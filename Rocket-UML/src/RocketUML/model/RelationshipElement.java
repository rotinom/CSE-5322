package RocketUML.model;


import RocketUML.ui.Drawable;
import RocketUML.visitor.Visitor;

enum RelationshipType{
  Inheritance,

};

/**
 * Created by rotinom on 2/19/14.
 */
public class RelationshipElement extends AbstractElement implements Drawable {

    private ClassElement srce; // Source
    private ClassElement dest; // Destination
    private ClassElement assc; // Associated class

    private String srceMultiplicity;
    private String destMultiplicity;




    private RelationshipElement() {
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public static RelationshipElement create() {
        return new RelationshipElement();
    }

    @Override
    public void draw() {
    }

    @Override
    public void destroy() {
    }


}
