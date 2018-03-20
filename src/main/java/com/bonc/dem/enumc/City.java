package com.bonc.dem.enumc;

public enum City {

    SY("沈阳", 1), //沈阳
    DL("大连", 2), //大连
    AS("鞍山", 3), //鞍山
    FS("抚顺", 4), //抚顺
    BX("本溪", 5), //本溪
    DD("丹东", 6), //丹东
    JZ("锦州", 7), //锦州
    YK("营口", 8), //营口
    FX("阜新", 9), //阜新
    LY("辽阳", 10), //辽阳
    TL("铁岭", 11), //铁岭
    CY("朝阳", 12), //朝阳
    PJ("盘锦", 13), //盘锦
    HL("葫芦岛", 14); //葫芦岛

    private String value;
    private Integer index;

    City(String value, Integer index) {
        this.value = value;
        this.index = index;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
