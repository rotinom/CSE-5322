package RocketUML.visitor;


import RocketUML.model.ProjectElement;

enum CodeTypeEnum{
    CPP,
//    JAVA
};


/**
 * Created by rotinom on 3/4/14.
 */
public class CodeGenerationController {

    private static CodeGenerationController instance_ = new CodeGenerationController();

    private CodeGenerationController(){}

    public static CodeGenerationController create(){
        return instance_;
    }


    public void generateCode(CodeTypeEnum type, ProjectElement project, String outputDir){
        switch(type){
            case CPP:
                generateCppCode(project, outputDir);
                break;
            default:
                break;
        }
    }

    public void generateCppCode(ProjectElement project, String outputDir){
        CppHeaderGenerator gen = new CppHeaderGenerator();
        project.accept(gen);
        System.out.println(gen.toString());
    }
}
