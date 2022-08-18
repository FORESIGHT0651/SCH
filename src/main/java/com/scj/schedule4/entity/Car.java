package com.scj.schedule4.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class Car implements Serializable {
    private static final long serialVersionUID = -77142658678448426L;
    @Id
    private Integer carid;
    private Integer userid;
    private String carnumber;
    private Double carvehicle;
    private Double carvolume;
    private Double carrate;
    private String caraddr;
    private String carlbs;
    private String cartype;
    private Double carwidth;
    private Double carheight;
    private Double carweight;
    private Integer carway;


    public Integer getCarid() {
        return carid;
    }
    public void setCarid(Integer carid) {
        this.carid = carid;
    }

    public Integer getUserid() {
        return userid;
    }
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getCarnumber() {
        return carnumber;
    }
    public void setCarnumber(String carnumber) {
        this.carnumber = carnumber;
    }

    public Double getCarvehicle() {
        return carvehicle;
    }
    public void setCarvehicle(Double carvehicle) {
        this.carvehicle = carvehicle;
    }

    public Double getCarvolume() {
        return carvolume;
    }
    public void setCarvolume(Double carvolume) {
        this.carvolume = carvolume;
    }

    public Double getCarrate() {
        return carrate;
    }
    public void setCarrate(Double carrate) {
        this.carrate = carrate;
    }

    public String getCaraddr() {
        return caraddr;
    }
    public void setCaraddr(String caraddr) {
        this.caraddr = caraddr;
    }

    public String getCarlbs() {
        return carlbs;
    }
    public void setCarlbs(String carlbs) {
        this.carlbs = carlbs;
    }

    public String getCartype() {
        return cartype;
    }
    public void setCartype(String cartype) {
        this.cartype = cartype;
    }

    public Double getCarwidth() {
        return carwidth;
    }
    public void setCarwidth(Double carwidth) {
        this.carwidth = carwidth;
    }

    public Double getCarheight() {
        return carheight;
    }
    public void setCarheight(Double carheight) {
        this.carheight = carheight;
    }

    public Double getCarweight() {
        return carweight;
    }
    public void setCarweight(Double carweight) {
        this.carweight = carweight;
    }

    public Integer getCarway() {
        return carway;
    }
    public void setCarway(Integer carway) {
        this.carway = carway;
    }

}
