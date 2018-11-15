package com.softwaresolution.db.userperformancerating.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.softwaresolution.db.userperformancerating.MyApplication;
import com.softwaresolution.db.userperformancerating.data_models.UserModelNew;
import com.softwaresolution.db.userperformancerating.userperfamance_avg.AdminWorkPerformanceActivity;
import com.softwaresolution.db.userperformancerating.userpermance.WorkPerformanceActivity;

import static android.support.v4.util.Preconditions.checkNotNull;

public class LoginActivityPresenter implements LoginActivityContract.Presenter{



    @NonNull
    private final LoginActivityContract.View mLoginView;


    @SuppressLint("RestrictedApi")
    LoginActivityPresenter(@NonNull LoginActivityContract.View loginView)
    {
        mLoginView = checkNotNull(loginView);

    }

    @Override
    public void performLogin(String username,String userpass) {

        UserModelNew userModelNew=getUsersNew(username,userpass);


        if(userModelNew != null ) {


            MyApplication.currentUser=userModelNew;


            if(userModelNew.getDesignation().toLowerCase().equals("admin"))
            {

                mLoginView.setLoginAdmin("");
            }
            else {

                mLoginView.setLoginUser("");
            }


        } else {

            MyApplication.currentUser=null;

            mLoginView.setLoginFailed("");


        }




    }


    public static UserModelNew getUsersNew (String user_name,String user_pass) {

        try {
            return new Select()
                    .from(UserModelNew.class)
                    .where("user_name = ? AND user_pass = ?", user_name, user_pass)
                    .executeSingle();
        }catch (Exception ex)
        {
            ex.toString();
        }

        return  null;
    }



    @Override
    public void start() {



    }
}
