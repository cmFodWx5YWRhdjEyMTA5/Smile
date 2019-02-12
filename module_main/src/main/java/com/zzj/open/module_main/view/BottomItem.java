package com.zzj.open.module_main.view;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2018/12/5 16:52
 * @desc :
 * @version: 1.0
 */
public class BottomItem {

    private String name;
    private int icon;
    public BottomItem(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

}
