package RocketUML.visitor;


import RocketUML.model.ProjectElement;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;



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


        try {

            // Write the hpp file
            CppHeaderGenerator hpp_gen = new CppHeaderGenerator();
            project.accept(hpp_gen);

            OutputStream h_file = new FileOutputStream(outputDir + "/" + project.getName() + ".hpp");
            h_file.write(hpp_gen.toString().getBytes());
            h_file.close();


            // Write the cpp file
            CppBodyGenerator cpp_gen = new CppBodyGenerator();
            project.accept(cpp_gen);

            OutputStream c_file = new FileOutputStream(outputDir + "/" + project.getName() + ".cpp");
            c_file.write(cpp_gen.toString().getBytes());
            c_file.close();

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
