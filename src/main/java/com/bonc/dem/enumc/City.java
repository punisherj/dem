package com.bonc.dem.enumc;

public enum City {

    SY("lnsy", 1), //沈阳
    DL("lndl", 2), //大连
    AS("lnas", 3), //鞍山
    FS("lnfs", 4), //抚顺
    BX("lnbx", 5), //本溪
    DD("lndd", 6), //丹东
    JZ("lnjz", 7), //锦州
    YK("lnyk", 8), //营口
    FX("lnfx", 9), //阜新
    LY("lnly", 10), //辽阳
    TL("lntl", 11), //铁岭
    CY("lncy", 12), //朝阳
    PJ("lnpj", 13), //盘锦
    HL("lnhl", 14); //葫芦岛

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
