package com.bonc.dem.entity;

import com.bonc.dem.util.IdUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "oc_excel")
public class ExcelEntity implements Serializable {

    @Id
    private String id;
    private Integer amount;
    private Integer success;
    private String city;
    private String date;

    public ExcelEntity() {

    }

    public ExcelEntity(Integer amount, Integer success, String city, String date) {
        this.id = IdUtils.getUUID();
        this.amount = amount;
        this.success = success;
        this.city = city;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
