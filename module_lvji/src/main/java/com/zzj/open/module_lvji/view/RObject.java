package com.zzj.open.module_lvji.view;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/6/18
 * @desc :
 * @version: 1.0
 */
public class RObject {

    private String objectRule = "#";// 匹配规则
    private String objectText;// 话题文本

    public String getObjectRule() {
        return objectRule;
    }

    public void setObjectRule(String objectRule) {
        this.objectRule = objectRule;
    }

    public String getObjectText() {
        return objectText;
    }

    public void setObjectText(String objectText) {
        this.objectText = objectText;
    }

}
