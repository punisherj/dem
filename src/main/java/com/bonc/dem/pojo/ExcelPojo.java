package com.bonc.dem.pojo;

public class ExcelPojo {
    private String city;
    private Integer amount;
    private Integer success;
    private Integer fail;

    public ExcelPojo() {
    }

    public ExcelPojo(String city, Integer success, Integer fail) {
        this.city = city;
        this.success = success;
        this.fail = fail;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getAmount() {
        return this.getSuccess()+this.getFail();
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getFail() {
        return fail;
    }

    public void setFail(Integer fail) {
        this.fail = fail;
    }
}
