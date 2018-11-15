package com.softwaresolution.db.userperformancerating;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.softwaresolution.db.userperformancerating.data_models.UserModelNew;

public class MyApplication  extends Application {


    //public static  UserModelOld currentUser=null;


    public static UserModelNew currentUser=null;

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            //Configuration dbConfiguration = new Configuration.Builder(this).setDatabaseName("Pickrand.db").addModelClass(UserModelOld.class).create();

            //ActiveAndroid.initialize(dbConfiguration);


            ActiveAndroid.initialize(this);
        }catch (Exception ex)
        {
                   ex.toString();
        }

    }
}
