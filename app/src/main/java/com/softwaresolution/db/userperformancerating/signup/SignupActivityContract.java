package com.softwaresolution.db.userperformancerating.signup;


import com.softwaresolution.db.userperformancerating.BasePresenter;
import com.softwaresolution.db.userperformancerating.BaseView;

public interface SignupActivityContract {


    interface View extends BaseView<Presenter> {

        void setSignUpAdmin(String message);
        void setSignUpUser(String message);
        void setSignUpFailed(String message);
    }

    interface Presenter extends BasePresenter {

        void performSignUp(String username, String userpass,String designation);


    }



}
