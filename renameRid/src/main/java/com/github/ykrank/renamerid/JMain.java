package com.github.ykrank.renamerid;

public class JMain {


    public static void main(String... args){
        SourceMain sourceMain = new SourceMain();
        sourceMain.parsePublicXml();
        sourceMain.rewriteJavaSource("/Users/ykrank/Android/Decompile/app/src/main/java/com/netease/cloudmusic/activity/LoadingActivity.java");
    }

    public static void print(Object object){
        System.out.println(object);
    }


}
