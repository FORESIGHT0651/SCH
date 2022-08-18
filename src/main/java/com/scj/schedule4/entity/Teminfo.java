package com.scj.schedule4.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class Teminfo implements Serializable {
    private static final long serialVersionUID = 999910069223684354L;
    @Id
    private Integer teminfoid;
    private Integer routeid;
    private String teminfoaddr;
    private Integer strategy;


    public Integer getTeminfoid() {
        return teminfoid;
    }

    public void setTeminfoid(Integer teminfoid) {
        this.teminfoid = teminfoid;
    }

    public Integer getRouteid() {
        return routeid;
    }

    public void setRouteid(Integer routeid) {
        this.routeid = routeid;
    }

    public String getTeminfoaddr() {
        return teminfoaddr;
    }

    public void setTeminfoaddr(String teminfoaddr) {
        this.teminfoaddr = teminfoaddr;
    }

    public Integer getStrategy() {
        return strategy;
    }

    public void setStrategy(Integer strategy) {
        this.strategy = strategy;
    }

}
