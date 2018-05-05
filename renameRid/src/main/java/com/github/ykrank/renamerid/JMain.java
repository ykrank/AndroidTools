package com.github.ykrank.renamerid;

public class JMain {


    public static void main(String... args){
        SourceMain sourceMain = new SourceMain();
        sourceMain.parseRfile();
        sourceMain.rewriteJavaSource();
    }

    public static void print(Object object){
        System.out.println(object);
    }


}
