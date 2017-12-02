package codeAnalyzer.parser.javaParser;

import codeAnalyzer.model.Component;
import codeAnalyzer.model.ComponentType;
import codeAnalyzer.model.ComponentsRelationship;
import codeAnalyzer.repository.ComponentRepository;
import codeAnalyzer.repository.ComponentsRelationshipRepository;
import com.github.javaparser.JavaToken;
import com.github.javaparser.ParseResult;
import com.github.javaparser.TokenRange;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.utils.SourceRoot;
import org.mozilla.javascript.ast.VariableDeclaration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class PackageParser {

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private ComponentsRelationshipRepository componentsRelationshipRepository;

    public void parsePackages(String route) throws IOException {
        SourceRoot sourceRoot = new SourceRoot(Paths.get(System.getProperty("user.home") + route));

        List<ParseResult<CompilationUnit>> results = sourceRoot.tryToParse();
        results.get(8).getResult().get().accept(new MethodVisitor(), null);
        List<CompilationUnit> allClasses = sourceRoot.getCompilationUnits();

        //Component component1 = new Component(ComponentType.CLASS, "User1");
        //componentRepository.save(component1);
        //Component component2 = new Component(ComponentType.CLASS, "User2");
        //componentRepository.save(component2);
        //ComponentsRelationship componentsRelationship = new ComponentsRelationship(2, component1, component2);
        //componentsRelationshipRepository.save(componentsRelationship);

        Component rootComponent = new Component(ComponentType.ROOT, "root");
        componentRepository.save(rootComponent);

        for(int i=0; i<allClasses.size(); i++) {
            CompilationUnit classs = allClasses.get(i);

            for(int j=0; j<classs.getChildNodes().size(); j++){
                Node classFile = classs.getChildNodes().get(j);
                Component packageComponent = null;

                if (classFile instanceof PackageDeclaration){
                    PackageDeclaration packageNode = (PackageDeclaration) classFile;
                    System.out.println(packageNode.getName() + " --- " + "package");

                    packageComponent = new Component(ComponentType.PACKAGE, packageNode.getNameAsString());
                    componentRepository.save(packageComponent);
                    ComponentsRelationship componentsRelationship = new ComponentsRelationship(2, rootComponent, packageComponent);
                    componentsRelationshipRepository.save(componentsRelationship);

                }else if(classFile instanceof ClassOrInterfaceDeclaration){
                    ClassOrInterfaceDeclaration classNode = (ClassOrInterfaceDeclaration)classFile;
                    System.out.println(classNode.getName() + " --- " + "class");

                    Component classComponent = new Component(ComponentType.CLASS, classNode.getNameAsString());
                    componentRepository.save(classComponent);
                    if(packageComponent!=null) {
                        ComponentsRelationship componentsRelationship =
                                new ComponentsRelationship(2, packageComponent, classComponent);
                        componentsRelationshipRepository.save(componentsRelationship);
                    }
                    for (int k=0; k<classNode.getChildNodes().size(); k++){
                        Node classPart = classNode.getChildNodes().get(k);
                        if (classPart instanceof FieldDeclaration){
                            FieldDeclaration fieldNode = (FieldDeclaration)classNode.getChildNodes().get(k);
                            for(int q=0; q<fieldNode.getChildNodes().size(); q++){
                                Node filedName = fieldNode.getChildNodes().get(q);
                                if (filedName instanceof FieldDeclaration){
                                    FieldDeclaration varr = (FieldDeclaration)filedName;

                                    for(int p=0; p<varr.getChildNodes().size(); p++){
                                        if (varr.getChildNodes().get(p) instanceof VariableDeclarator){
                                            VariableDeclarator varrr = (VariableDeclarator)varr.getChildNodes().get(p);
                                            if(varrr.getNameAsString().equals("name"))
                                                System.out.println(varrr.getName() + "---" + "field");



                                        }
                                    }

                                }else if(filedName instanceof VariableDeclarator){
                                    VariableDeclarator varrr = (VariableDeclarator)filedName;
                                    if(varrr.getNameAsString().equals("name"))
                                        System.out.println(varrr.getName() + "---" + "field");
                                }
                            }
                        }else if(classPart instanceof MethodDeclaration) {
                            MethodDeclaration method = (MethodDeclaration) classPart;
                            for (int q = 0; q < method.getChildNodes().size(); q++) {
                                Node methodPart = method.getChildNodes().get(q);
                                if (methodPart instanceof SimpleName) {
                                    System.out.println((SimpleName) methodPart + "---" + "method");

                                    Component methodComponent = new Component(ComponentType.METHOD, ((SimpleName)methodPart).asString());
                                    componentRepository.save(methodComponent);
                                    ComponentsRelationship componentsRelationship2 =
                                            new ComponentsRelationship(2, classComponent, methodComponent);
                                    componentsRelationshipRepository.save(componentsRelationship2);
                                }
                            }
                        }
                    }
                }

            }
        }
        /*TokenRange token = componentClass.getTokenRange().get();
        JavaToken start = token.getBegin();
        while(start!=null){
            System.out.print(start.getText());
            if(start.getNextToken().isPresent())
                start = start.getNextToken().get();
            else
                break;
        }*/
        System.out.println("parsirano");
    }

    private static class MethodVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration n, Void arg) {
            System.out.println(n.getName());
        }
    }

    private static class PackageVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(PackageDeclaration n, Void args) {
            System.out.println(n.getName());
        }
    }

}
