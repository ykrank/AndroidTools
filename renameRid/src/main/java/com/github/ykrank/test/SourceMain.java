package com.github.ykrank.test;

import com.github.ykrank.renamerid.JMain;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

public class SourceMain {
    static final String packageName = "com.netease.cloudmusic";
    static final String javaDir = "/Users/ykrank/Android/Decompile/app/src/main/java";

    private Pattern rPattern;

    SourceMain() {
        this.rPattern = Pattern.compile("a\\.auu\\.a\\.c\\(\"(.+?)\"\\)");
    }

    private File checkRootDir() {
        File rootDir = new File(javaDir + File.separator + packageName.replace(".", File.separator));
        if (!rootDir.exists() || !rootDir.isDirectory()) {
            throw new IllegalArgumentException("JavaDir should be Directory:" + rootDir.getAbsolutePath());
        }
        return rootDir;
    }

    /**
     * 将源码中的id值重写为R文件中的名称
     */
    public void rewriteJavaSourceDir() {
        listDir(checkRootDir(), true);
    }

    public void rewriteJavaSourceFile(String path) {
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
                sink.writeUtf8(rewriteDecryptString(line));
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

    private String rewriteDecryptString(String string) {
        Matcher matcher = rPattern.matcher(string);
        while (matcher.find()) {
            String origin = matcher.group(0);
            String encrypt = matcher.group(1);
            String decrypt = CouldMusicDecrypt.c(encrypt);
            if (decrypt != null && !decrypt.isEmpty()) {
                string = string.replace(origin, "\"" + decrypt + "\"");
            }
        }
        return string;
    }
}
