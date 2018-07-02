package com.github.ykrank.renamerid;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.Expression;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

public class SourceMain {
    static final String packageName = "com.netease.cloudmusic";
    static final String javaDir = "/Users/ykrank/Android/Decompile/app/src/main/java";
    static final String publicXmlDir = "/Users/ykrank/Android/Decompile/app/src/main/res/values/public.xml";

    private Map<Integer, String> rNameMaps = new HashMap<>();

    private Pattern rPattern;

    SourceMain() {
        this.rPattern = Pattern.compile("[1-9]\\d{7,}");
    }

    private File checkRootDir() {
        File rootDir = new File(javaDir + File.separator + packageName.replace(".", File.separator));
        if (!rootDir.exists() || !rootDir.isDirectory()) {
            throw new IllegalArgumentException("JavaDir should be Directory");
        }
        return rootDir;
    }

    private File checkRfile() {
        File rFile = new File(checkRootDir(), "R.java");
        if (!rFile.exists() || !rFile.isFile()) {
            throw new IllegalArgumentException("R.java should be java file");
        }
        return rFile;
    }

    /**
     * 解析反编译出的R.java
     */
    public void parseRfile() {
        try {
            FileInputStream in = new FileInputStream(checkRfile());
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
     * 解析apktool反编译出的res\values\public.xml
     */
    public void parsePublicXml() {
        try {
            File f = new File(publicXmlDir);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(f);
            NodeList childNodes = doc.getDocumentElement().getElementsByTagName("public");
            for (int i = 0; i < childNodes.getLength(); i++) {
                NamedNodeMap nodeAttr = childNodes.item(i).getAttributes();
                RItem rItem = new RItem(nodeAttr.getNamedItem("type").getNodeValue(),
                        nodeAttr.getNamedItem("name").getNodeValue(),
                        Integer.parseUnsignedInt(nodeAttr.getNamedItem("id").getNodeValue().substring(2), 16));
                rNameMaps.put(rItem.getValue(), "R." + rItem.getType() + "." + rItem.getName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将源码中的id值重写为R文件中的名称
     */
    public void rewriteJavaSource() {
        listDir(checkRootDir(), true);
    }

    public void rewriteJavaSource(String path) {
        rewriteFile(new File(path));
    }

    /**
     * 遍历文件夹
     *
     * @param dir             文件夹
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
        if (!file.getName().endsWith(".java") || file.getName().endsWith(".tmp.java")) {
            return;
        }
        JMain.print("rewrite:" + file.getAbsolutePath());

        BufferedSink sink = null;
        try {
            BufferedSource source = Okio.buffer(Okio.source(file));
            sink = Okio.buffer(Okio.sink(new File(file.getParentFile(), file.getName() + ".tmp.java")));
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
            if (sink != null) {
                try {
                    sink.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String rewriteRString(String string) {
        Matcher matcher = rPattern.matcher(string);
        while (matcher.find()) {
            String code = matcher.group();
            try {
                String names = rNameMaps.get(Integer.valueOf(code));
                if (names != null && !names.isEmpty()) {
                    string = string.replace(code, names);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return string;
    }
}
