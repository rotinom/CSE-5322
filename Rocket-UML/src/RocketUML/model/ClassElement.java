package RocketUML.model;

import RocketUML.ui.Drawable;

/**
 * Created by rotinom on 2/19/14.
 */
public class ClassElement extends AbstractElement implements Drawable {

    private ClassElement() {
    }

    public static ClassElement create() {
        return new ClassElement();
    }

    @Override
    public void draw() {

    }
}
