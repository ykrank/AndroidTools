package com.github.ykrank.renamerid;

/**
 * R.java 中的每条记录。忽略styleable，因为它的属性不会在代码中体现。
 */
public class RItem {
    /**
     * 类型。如R.id.text1中的 id
     */
    private String type;
    /**
     * 名称。如R.id.text1中的 text1
     */
    private String name;
    /**
     * 具体id
     */
    private int value;

    public RItem(String type, String name, int value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
