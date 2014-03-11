package RocketUML.model;


import RocketUML.ui.Drawable;
import RocketUML.visitor.Visitor;

import javax.management.relation.Relation;

;

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

    public static RelationshipElement create(RelationshipType type){
        return new RelationshipElement(type);
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

    public RelationshipElement setSrceMultiplicity(String srceMultiplicity) {
        this.srceMultiplicity = srceMultiplicity;
        return this;
    }

    public String getDestMultiplicity() {
        return destMultiplicity;
    }

    public RelationshipElement setDestMultiplicity(String destMultiplicity) {
        this.destMultiplicity = destMultiplicity;
        return this;
    }

    public ClassElement getAssc() {
        return assc;
    }

    public RelationshipElement setAssc(ClassElement assc) {
        this.assc = assc;
        return this;
    }

    public ClassElement getDest() {
        return dest;
    }

    public RelationshipElement setDest(ClassElement dest) {
        this.dest = dest;
        return this;
    }

    public ClassElement getSrce() {
        return srce;
    }

    public RelationshipElement setSrce(ClassElement srce) {
        this.srce = srce;
        return this;
    }

    public RelationshipType getType() {return type_;}
}
