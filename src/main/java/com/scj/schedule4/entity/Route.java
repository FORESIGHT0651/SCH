package com.scj.schedule4.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class Route implements Serializable {
    private static final long serialVersionUID = 695346761495116080L;
    @Id
    private Integer routeid;
    private Integer carid;
    private Double routeweight;
    private Double routevolume;
    private Integer routenum;
    private Integer routetotal;
    private String routetime;
    private String routestate;

    public Integer getRouteid() {
        return routeid;
    }
    public void setRouteid(Integer routeid) {
        this.routeid = routeid;
    }

    public Integer getCarid() {
        return carid;
    }
    public void setCarid(Integer carid) {
        this.carid = carid;
    }

    public Double getRouteweight() {
        return routeweight;
    }
    public void setRouteweight(Double routeweight) {
        this.routeweight = routeweight;
    }

    public Double getRoutevolume() {
        return routevolume;
    }
    public void setRoutevolume(Double routevolume) {
        this.routevolume = routevolume;
    }

    public Integer getRoutenum() {
        return routenum;
    }
    public void setRoutenum(Integer routenum) {
        this.routenum = routenum;
    }

    public Integer getRoutetotal() {
        return routetotal;
    }
    public void setRoutetotal(Integer routetotal) {
        this.routetotal = routetotal;
    }

    public String getRoutetime() {
        return routetime;
    }
    public void setRoutetime(String routetime) {
        this.routetime = routetime;
    }

    public String getRoutestate() {
        return routestate;
    }
    public void setRoutestate(String routestate) {
        this.routestate = routestate;
    }

}