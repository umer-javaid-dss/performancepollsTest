package com.softwaresolution.db.userperformancerating.data_models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;


@Table(name = "RatingModel")

public class RatingModel extends Model {




          @Column(name = "work1_rating")
          private int work1_rating;

          @Column(name = "work2_rating")
          private int work2_rating;


          @Column(name = "work3_rating")
          private int work3_rating;





    public int getWork1_rating() {
        return work1_rating;
    }

    public void setWork1_rating(int work1_rating) {
        this.work1_rating = work1_rating;
    }

    public int getWork2_rating() {
        return work2_rating;
    }

    public void setWork2_rating(int work2_rating) {
        this.work2_rating = work2_rating;
    }

    public int getWork3_rating() {
        return work3_rating;
    }

    public void setWork3_rating(int work3_rating) {
        this.work3_rating = work3_rating;
    }
}
