package com.scj.schedule4.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class Good implements Serializable {
    private static final long serialVersionUID = -91355814168521376L;
    @Id
    private Integer goodid;
    private Integer routeid;
    private Double goodweight;
    private Double goodvolume;
    private Integer goodnum;
    private String startaddr;
    private String endaddr;
    private String startlbs;
    private String endlbs;
    private String goodtime;
    private String goodstate;


    public Integer getGoodid() {
        return goodid;
    }
    public void setGoodid(Integer goodid) {
        this.goodid = goodid;
    }

    public Integer getRouteid() {
        return routeid;
    }
    public void setRouteid(Integer routeid) {
        this.routeid = routeid;
    }

    public Double getGoodweight() {
        return goodweight;
    }
    public void setGoodweight(Double goodweight) {
        this.goodweight = goodweight;
    }

    public Double getGoodvolume() {
        return goodvolume;
    }
    public void setGoodvolume(Double goodvolume) {
        this.goodvolume = goodvolume;
    }

    public Integer getGoodnum() {
        return goodnum;
    }
    public void setGoodnum(Integer goodnum) {
        this.goodnum = goodnum;
    }

    public String getStartaddr() {
        return startaddr;
    }
    public void setStartaddr(String startaddr) {
        this.startaddr = startaddr;
    }

    public String getEndaddr() {
        return endaddr;
    }
    public void setEndaddr(String endaddr) {
        this.endaddr = endaddr;
    }

    public String getStartlbs() {
        return startlbs;
    }
    public void setStartlbs(String startlbs) {
        this.startlbs = startlbs;
    }

    public String getEndlbs() {
        return endlbs;
    }
    public void setEndlbs(String endlbs) {
        this.endlbs = endlbs;
    }

    public String getGoodtime() {
        return goodtime;
    }
    public void setGoodtime(String goodtime) {
        this.goodtime = goodtime;
    }

    public String getGoodstate() {
        return goodstate;
    }
    public void setGoodstate(String goodstate) {
        this.goodstate = goodstate;
    }

}
