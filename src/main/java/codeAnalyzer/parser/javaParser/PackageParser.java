package codeAnalyzer.parser.javaParser;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.utils.SourceRoot;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Service
public class PackageParser {

    public void parsePackages(String route) throws IOException {
        SourceRoot sourceRoot = new SourceRoot(Paths.get(System.getProperty("user.home") + route));

        List<ParseResult<CompilationUnit>> results = sourceRoot.tryToParse();
        results.get(8).getResult().get().accept(new MethodVisitor(), null);
        List<CompilationUnit> compilationUnits = sourceRoot.getCompilationUnits();
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
