package RocketUML.model;


import RocketUML.ui.Drawable;

/**
 * Created by rotinom on 2/19/14.
 */
public class RelationshipElement extends AbstractElement implements Drawable {

    private RelationshipElement() {
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
