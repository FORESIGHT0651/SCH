package com.scj.schedule4.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -88949149619494706L;
    @Id
    private Integer userid;
    private String useraccount;
    private String userpassword;
    private String username;
    private String useridentity;
    private String userdriver;
    private String userlevel;


    public Integer getUserid() {
        return userid;
    }
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUseraccount() {
        return useraccount;
    }
    public void setUseraccount(String useraccount) {
        this.useraccount = useraccount;
    }

    public String getUserpassword() {
        return userpassword;
    }
    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUseridentity() {
        return useridentity;
    }
    public void setUseridentity(String useridentity) {
        this.useridentity = useridentity;
    }

    public String getUserdriver() {
        return userdriver;
    }
    public void setUserdriver(String userdriver) {
        this.userdriver = userdriver;
    }

    public String getUserlevel() {
        return userlevel;
    }
    public void setUserlevel(String userlevel) {
        this.userlevel = userlevel;
    }



}