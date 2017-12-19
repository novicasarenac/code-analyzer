package codeAnalyzer.parser;

import codeAnalyzer.model.Component;
import codeAnalyzer.model.ComponentType;
import codeAnalyzer.model.ComponentsRelationship;
import codeAnalyzer.repository.ComponentRepository;
import codeAnalyzer.repository.ComponentsRelationshipRepository;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.utils.SourceRoot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Service
public class PackageParser {

    private static final int ROOT_PACKAGE_COST  = 4;
    private static final int PACKAGE_CLASS_COST = 3;
    private static final int CLASS_METHOD_COST  = 2;

    @Autowired
    private ComponentRepository componentRepository;
    @Autowired
    private ComponentsRelationshipRepository componentsRelationshipRepository;

    public void parsePackages(String route) throws IOException {
        SourceRoot sourceRoot = new SourceRoot(Paths.get(System.getProperty("user.home") + route));

        List<ParseResult<CompilationUnit>> results = sourceRoot.tryToParse();
        results.get(8).getResult().get().accept(new MethodVisitor(), null);
        List<CompilationUnit> nodes = sourceRoot.getCompilationUnits();

        Component rootComponent = new Component(ComponentType.ROOT, "root");
        componentRepository.save(rootComponent);

        for (int i = 0; i < nodes.size(); i++) {
            CompilationUnit clazz = nodes.get(i);
            Component packageComponent = null;

            for (int j = 0; j < clazz.getChildNodes().size(); j++) {
                Node node = clazz.getChildNodes().get(j);

                if (node instanceof PackageDeclaration) {
                    PackageDeclaration packageNode = (PackageDeclaration) node;

                    List<Component> packages = componentRepository.findByName(packageNode.getName().getIdentifier());
                    if (packages.size() == 0) {
                        packageComponent = new Component(ComponentType.PACKAGE, packageNode.getName().getIdentifier());
                        componentRepository.save(packageComponent);
                        componentsRelationshipRepository.save(new ComponentsRelationship(ROOT_PACKAGE_COST, rootComponent, packageComponent));
                    } else
                        packageComponent = packages.get(0);

                } else if (node instanceof ClassOrInterfaceDeclaration) {
                    ClassOrInterfaceDeclaration classNode = (ClassOrInterfaceDeclaration) node;

                    Component classComponent = new Component(ComponentType.CLASS, classNode.getNameAsString());
                    componentRepository.save(classComponent);
                    if (packageComponent != null) {
                        componentsRelationshipRepository.save(new ComponentsRelationship(PACKAGE_CLASS_COST, packageComponent, classComponent));
                    } else {
                        componentsRelationshipRepository.save(new ComponentsRelationship(CLASS_METHOD_COST, rootComponent, classComponent));
                    }
                    for (int k = 0; k < classNode.getChildNodes().size(); k++) {
                        Node classPart = classNode.getChildNodes().get(k);
                        if (classPart instanceof MethodDeclaration) {
                            MethodDeclaration method = (MethodDeclaration) classPart;
                            for (int q = 0; q < method.getChildNodes().size(); q++) {
                                Node methodPart = method.getChildNodes().get(q);
                                if (methodPart instanceof SimpleName) {
                                    Component methodComponent = new Component(ComponentType.METHOD, ((SimpleName) methodPart).asString());
                                    componentRepository.save(methodComponent);
                                    componentsRelationshipRepository.save(new ComponentsRelationship(CLASS_METHOD_COST, classComponent, methodComponent));
                                }
                            }
                        }
                    }
                }
            }
        }
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
