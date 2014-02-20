package RocketUML.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rotinom on 2/19/14.
 */
public class ProjectElement {

    private List<AbstractElement> diagramList_ = new ArrayList<AbstractElement>();

    public void addDiagram(AbstractElement ae){
        diagramList_.add(ae);
    }

}
