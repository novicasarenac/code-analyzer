package codeAnalyzer.parser;

import codeAnalyzer.model.Component;
import codeAnalyzer.model.ComponentType;
import codeAnalyzer.model.ComponentsRelationship;
import codeAnalyzer.model.SectionComponent;
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
import com.github.javaparser.ast.expr.Name;
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

        Component rootComponent = new Component(ComponentType.ROOT, "root");
        componentRepository.save(rootComponent);


        for(int i=0; i<allClasses.size(); i++) {
            CompilationUnit classs = allClasses.get(i);
            Component packageComponent = null;

            for(int j=0; j<classs.getChildNodes().size(); j++){
                Node classFile = classs.getChildNodes().get(j);

                if (classFile instanceof PackageDeclaration) {
                    PackageDeclaration packageNode = (PackageDeclaration) classFile;
                    System.out.println(packageNode.getNameAsString() + " --- " + "package");

                    List<Component> packages = componentRepository.findByName(packageNode.getName().getIdentifier());
                    if (packages.size() == 0) {
                        packageComponent = new Component(ComponentType.PACKAGE, packageNode.getName().getIdentifier());
                        componentRepository.save(packageComponent);
                        ComponentsRelationship componentsRelationship = new ComponentsRelationship(2, rootComponent, packageComponent);
                        componentsRelationshipRepository.save(componentsRelationship);
                    } else
                        packageComponent = packages.get(0);

                }else if(classFile instanceof ClassOrInterfaceDeclaration){
                    ClassOrInterfaceDeclaration classNode = (ClassOrInterfaceDeclaration)classFile;
                    System.out.println(classNode.getName() + " --- " + "class");

                    Component classComponent = new Component(ComponentType.CLASS, classNode.getNameAsString());
                    componentRepository.save(classComponent);
                    if(packageComponent!=null) {
                        ComponentsRelationship componentsRelationship =
                                new ComponentsRelationship(2, packageComponent, classComponent);
                        componentsRelationshipRepository.save(componentsRelationship);
                    }else{
                        ComponentsRelationship componentsRelationship =
                                new ComponentsRelationship(2, rootComponent, classComponent);
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

                                    SectionComponent section = new SectionComponent("if", 4, 8);
                                    componentRepository.save(section);
                                    ComponentsRelationship componentsRelationship3 =
                                            new ComponentsRelationship(2, methodComponent, section);
                                    componentsRelationshipRepository.save(componentsRelationship3);
                                }
                            }
                        }
                    }
                }

            }
        }
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
