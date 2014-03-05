package RocketUML.model;


import RocketUML.ui.Drawable;
import RocketUML.visitor.Visitor;

enum RelationshipType{
    Inheritance,
    Composition,
    Association
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

    private RelationshipType type_;


    public RelationshipElement(RelationshipType type) {
        type_ = type;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public void draw() {
    }

    @Override
    public void destroy() {
    }


    public String getSrceMultiplicity() {
        return srceMultiplicity;
    }

    public void setSrceMultiplicity(String srceMultiplicity) {
        this.srceMultiplicity = srceMultiplicity;
    }

    public String getDestMultiplicity() {
        return destMultiplicity;
    }

    public void setDestMultiplicity(String destMultiplicity) {
        this.destMultiplicity = destMultiplicity;
    }

    public ClassElement getAssc() {
        return assc;
    }

    public void setAssc(ClassElement assc) {
        this.assc = assc;
    }

    public ClassElement getDest() {
        return dest;
    }

    public void setDest(ClassElement dest) {
        this.dest = dest;
    }

    public ClassElement getSrce() {
        return srce;
    }

    public void setSrce(ClassElement srce) {
        this.srce = srce;
    }
}
