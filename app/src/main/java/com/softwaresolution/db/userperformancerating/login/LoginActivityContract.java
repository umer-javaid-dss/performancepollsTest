package com.softwaresolution.db.userperformancerating.login;


import com.softwaresolution.db.userperformancerating.BasePresenter;
import com.softwaresolution.db.userperformancerating.BaseView;

public interface  LoginActivityContract {


    interface View extends BaseView<Presenter> {

        void setLoginAdmin(String message);
        void setLoginUser(String message);
        void setLoginFailed(String message);
    }

    interface Presenter extends BasePresenter {

        void performLogin(String username,String userpass);


    }



}
