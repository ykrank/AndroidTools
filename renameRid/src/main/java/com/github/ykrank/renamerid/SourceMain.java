package com.github.ykrank.renamerid;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.Expression;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

public class SourceMain {
    static final String packageName = "com.cjsc.mobile";
    static final String javaDir = "/Users/ykrank/Android/CjscXposed/app/src/main/java";

    private File rootDir;
    private File rFile;

    private Map<Integer, String> rNameMaps = new HashMap<>();

    private Pattern rPattern;

    SourceMain() {
        this.rootDir = checkRootDir();
        this.rFile = checkRfile();
        this.rPattern = Pattern.compile("[1-9]\\d{7,}");
    }

    public File checkRootDir() {
        File rootDir = new File(javaDir + File.separator + packageName.replace(".", File.separator));
        if (!rootDir.exists() || !rootDir.isDirectory()) {
            throw new IllegalArgumentException("JavaDir should be Directory");
        }
        return rootDir;
    }

    public File checkRfile() {
        File rFile = new File(rootDir, "R.java");
        if (!rFile.exists() || !rFile.isFile()) {
            throw new IllegalArgumentException("R.java should be java file");
        }
        return rFile;
    }

    public void parseRfile() {
        try {
            FileInputStream in = new FileInputStream(rFile);
            CompilationUnit cu = JavaParser.parse(in);
            Optional<ClassOrInterfaceDeclaration> rClass = cu.findFirst(ClassOrInterfaceDeclaration.class, it -> it.isPublic() && it.isFinal());
            if (!rClass.isPresent() || !"R".equals(rClass.get().getName().asString())) {
                throw new IllegalArgumentException("R.java should include R class");
            }
            rClass.get().findAll(ClassOrInterfaceDeclaration.class, it -> {
                String className = it.getName().asString();
                return !"R".equals(className) && !"styleable".equals(className);
            })
                    .stream()
                    .flatMap(it -> it.findAll(FieldDeclaration.class, f -> f.isPublic() && f.isFinal() && f.isStatic())
                            .stream()
                            .flatMap(f -> f.getVariables().stream())
                            .filter(f -> {
                                Optional<Expression> init = f.getInitializer();
                                return init.isPresent() && init.get().isIntegerLiteralExpr();
                            })
                            .map(f -> new RItem(it.getName().asString(), f.getName().asString(), f.getInitializer().get().asIntegerLiteralExpr().asInt()))
                    ).forEach(it -> rNameMaps.put(it.getValue(), "R." + it.getType() + "." + it.getName()));
//            rNameMaps.forEach((key, value)->JMain.print(value));
            JMain.print("R size:" + rNameMaps.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将源码中的id值重写为R文件中的名称
     */
    public void rewriteJavaSource() {
        listDir(rootDir, true);
    }

    /**
     * 遍历文件夹
     *
     * @param dir            文件夹
     * @param ignoreRootRFile 是否忽略文件夹根目录的R.java
     */
    private void listDir(File dir, boolean ignoreRootRFile) {
        if (!dir.isDirectory()) {
            return;
        }
        File[] childFiles = dir.listFiles();
        if (childFiles != null && childFiles.length > 0) {
            Arrays.stream(childFiles)
                    .forEach(it -> {
                        if (it.isFile()) {
                            if (ignoreRootRFile && it.getName().equals("R.java")) {

                            } else {
                                rewriteFile(it);
                            }
                        } else {
                            listDir(it, false);
                        }
                    });
        }
    }

    private void rewriteFile(File file) {
        if (!file.isFile()) {
            return;
        }
        if (!file.getName().endsWith(".java") || file.getName().endsWith(".tmp.java")){
            return;
        }
        JMain.print("rewrite:" + file.getAbsolutePath());

        BufferedSink sink = null;
        try {
            BufferedSource source = Okio.buffer(Okio.source(file));
            sink = Okio.buffer(Okio.sink(new File(file.getParentFile(), file.getName()+".tmp.java")));
            while (true) {
                String line = source.readUtf8Line();
                if (line == null) {
                    break;
                }
                sink.writeUtf8(rewriteRString(line));
                sink.writeUtf8("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sink != null){
                try {
                    sink.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String rewriteRString(String string){
        Matcher matcher = rPattern.matcher(string);
        while (matcher.find()){
            String code = matcher.group();
            String names = rNameMaps.get(Integer.valueOf(code));
            if (names != null && !names.isEmpty()){
                string = string.replace(code, names);
            }
        }
        return string;
    }
}
