package RocketUML.model;


import RocketUML.ui.Drawable;
import RocketUML.visitor.Visitor;

/**
 * Created by rotinom on 2/19/14.
 */
public class RelationshipElement extends AbstractElement implements Drawable {

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
