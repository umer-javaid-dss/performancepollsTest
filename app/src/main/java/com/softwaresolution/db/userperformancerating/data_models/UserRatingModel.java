package com.softwaresolution.db.userperformancerating.data_models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;


@Table(name = "UserRatingModel")

public class UserRatingModel extends Model {



            @Column(name = "rating_id")
            private long rating_id;


            @Column(name = "user_id")
            private long user_id;

            @Column(name = "form_user")
            private long form_user;


            @Column(name = "timestamp", index = true)
            private Date timestamp;


    public long getRating_id() {
        return rating_id;
    }

    public void setRating_id(long rating_id) {
        this.rating_id = rating_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getForm_user() {
        return form_user;
    }

    public void setForm_user(long form_user) {
        this.form_user = form_user;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
