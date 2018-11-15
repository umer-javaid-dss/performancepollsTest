package com.softwaresolution.db.userperformancerating.data_models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;


@Table(name = "UserModelNew")

public class UserModelNew extends Model {




     @Column(name = "name")
     private String name;

     @Column(name = "designation")
      private String designation;


      @Column(name = "user_name")
      private String user_name;


      @Column(name = "user_pass")
      private String user_pass;

      @Column(name = "picture")
      private byte [] picture;



      @Column(name = "timestamp", index = true)
       private Date timestamp;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }



    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
