package RocketUML.visitor;

import RocketUML.model.*;

/**
 * Created by rotinom on 3/1/14.
 */
public abstract class Visitor {
    public abstract void visit(ClassElement data);
    public abstract void visit(AttributeElement data);
    public abstract void visit(MethodElement data);
    public abstract void visit(ProjectElement data);
    public abstract void visit(DiagramElement data);
    public abstract void visit(RelationshipElement data);
    public abstract void visit(MethodParameter methodParameter);
}
