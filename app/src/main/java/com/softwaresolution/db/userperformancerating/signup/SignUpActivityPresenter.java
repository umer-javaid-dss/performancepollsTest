package com.softwaresolution.db.userperformancerating.signup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.softwaresolution.db.userperformancerating.MyApplication;
import com.softwaresolution.db.userperformancerating.data_models.UserModelNew;
import com.softwaresolution.db.userperformancerating.login.LoginActivityContract;
import com.softwaresolution.db.userperformancerating.userperfamance_avg.AdminWorkPerformanceActivity;
import com.softwaresolution.db.userperformancerating.userpermance.WorkPerformanceActivity;

import static android.support.v4.util.Preconditions.checkNotNull;
import static com.softwaresolution.db.userperformancerating.login.UserLoginActivity.getUsersNew;

public class SignUpActivityPresenter implements SignupActivityContract.Presenter{



    @NonNull
    private final SignupActivityContract.View mSignUpView;


    @SuppressLint("RestrictedApi")
    SignUpActivityPresenter(@NonNull SignupActivityContract.View signUpView)
    {
        mSignUpView = checkNotNull(signUpView);

    }


    @Override
    public void performSignUp(String username, String userpass, String designation) {

        UserModelNew userModelNew=getUsersNew(username,username);


        if(userModelNew!=null)
        {
            mSignUpView.setSignUpFailed("User exist already!");
            return;
        }



        UserModelNew  newUser= new UserModelNew();
        newUser.setName(username);
        newUser.setUser_pass(userpass);
        newUser.setUser_name(username);
        newUser.setDesignation(designation.toLowerCase());
        newUser.save();


        MyApplication.currentUser=newUser;

        if(newUser.getDesignation().toLowerCase().equals("admin"))
        {
            mSignUpView.setSignUpAdmin("");
        }
        else {

            mSignUpView.setSignUpUser("");

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
