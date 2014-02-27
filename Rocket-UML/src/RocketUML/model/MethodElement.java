package RocketUML.model;

/**
 * Created by rotinom on 2/26/14.
 */
public class MethodElement {
    private String protection; /// public/protected/private
    private String modifier;   /// const
    private String name;       /// name
    private String returnType; /// return value

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getProtection() {
        return protection;
    }

    public void setProtection(String protection) {
        this.protection = protection;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }
}
