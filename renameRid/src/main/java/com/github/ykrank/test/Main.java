package com.github.ykrank.test;

public class Main {

    public static void main(String... args) {
        try {
            SourceMain sourceMain = new SourceMain();
            sourceMain.rewriteJavaSourceFile("/Users/ykrank/Android/Decompile/app/src/main/java/com/netease/cloudmusic/activity/LoadingActivity.java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
