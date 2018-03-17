package com.bonc.dem.pojo;

//@Entity
public class Excel {

    private Integer count;
    private Integer success;
    private Integer fail;
    private double successPercent;

    public Excel() {

    }

    public Excel(Integer count, Integer success, Integer fail, double successPercent) {
        this.count = count;
        this.success = success;
        this.fail = fail;
        this.successPercent = successPercent;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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

    public double getSuccessPercent() {
        return successPercent;
    }

    public void setSuccessPercent(double successPercent) {
        this.successPercent = successPercent;
    }
}
