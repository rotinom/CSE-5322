package RocketUML.visitor;

import RocketUML.model.*;

/**
 * Created by rotinom on 3/1/14.
 */
public abstract class Visitor {
    public void visit(ClassElement data){};
    public void visit(AttributeElement data){};
    public void visit(MethodElement data){};
    public void visit(ProjectElement data){};
    public void visit(DiagramElement data){};
    public void visit(RelationshipElement data){};
    public void visit(MethodParameter methodParameter){};
}
